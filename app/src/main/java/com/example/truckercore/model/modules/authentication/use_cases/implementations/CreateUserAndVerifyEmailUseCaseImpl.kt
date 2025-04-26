package com.example.truckercore.model.modules.authentication.use_cases.implementations

import com.example.truckercore.model.infrastructure.integration.auth.for_app.repository.AuthenticationRepository
import com.example.truckercore.model.infrastructure.integration.auth.for_app.requirements.EmailCredential
import com.example.truckercore.model.infrastructure.integration.auth.for_app.requirements.UserProfile
import com.example.truckercore.model.modules.authentication.use_cases.NewEmailResult
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.CreateUserAndVerifyEmailUseCase
import com.example.truckercore.model.infrastructure.utils.task_manager.TaskManager
import com.example.truckercore.model.shared.utils.expressions.handleAppResult
import com.example.truckercore.model.shared.utils.expressions.logError
import com.example.truckercore.model.shared.utils.expressions.mapAppResult
import com.example.truckercore.model.shared.utils.expressions.onEarlyExit
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll

typealias EarlyExit = Boolean

internal class CreateUserAndVerifyEmailUseCaseImpl(
    private val authRepository: AuthenticationRepository,
    private val userTask: TaskManager<Unit>,
    private val nameTask: TaskManager<Unit>,
    private val emailTask: TaskManager<Unit>,
) : CreateUserAndVerifyEmailUseCase {

    override suspend fun invoke(credential: EmailCredential): NewEmailResult {
        return try {
            // Try creating the user and exit early if failed
            createUser(credential).onEarlyExit { return getResult() }

            // Proceed with updating name and sending email verification concurrently
            updateNameAndSendEmailAsync(credential)

            // Return the final result after all tasks are completed
            getResult()

        } catch (e: Exception) {
            // Log errors if any exception occurs and return the result
            logError("An error occurred while authenticating and verifying a new Email: $e.")
            getResult()
        } finally {
            // Clear tasks value and let ready to another email registration
            clearTasks()
        }
    }

    private suspend fun createUser(credential: EmailCredential): EarlyExit {
        return authRepository.createUserWithEmail(credential.email, credential.password)
            .mapAppResult(
                success = {
                    // Mark the task as successful with the Firebase user
                    userTask.onSuccess(Unit)
                    false // No early exit
                },
                error = { e ->
                    // Handle error during user creation
                    userTask.onError(e)
                    true // Early exit
                }
            )
    }

    private suspend fun updateNameAndSendEmailAsync(credential: EmailCredential): Unit =
        coroutineScope {
            val nameDef = async { updateName(credential) }
            val emailDef = async { sendEmail() }
            joinAll(nameDef, emailDef)
        }

    private suspend fun updateName(credential: EmailCredential) {
        val profile = UserProfile(credential.name)
        authRepository.updateUserProfile(profile).handleAppResult(
            success = { nameTask.onSuccess(Unit) },
            error = { e -> nameTask.onError(e) }
        )

    }

    private suspend fun sendEmail() {
        authRepository.sendEmailVerification()
            .handleAppResult(
                success = { emailTask.onSuccess(Unit) },
                error = { e -> emailTask.onError(e) }
            )
    }

    private fun getResult(): NewEmailResult {
        val errorSet = mutableSetOf<Exception>().apply {
            userTask.exception?.let { add(it) }
            nameTask.exception?.let { add(it) }
            emailTask.exception?.let { add(it) }
        }

        return NewEmailResult(
            userCreated = userTask.isSuccess,
            nameUpdated = nameTask.isSuccess,
            emailSent = emailTask.isSuccess,
            errors = errorSet
        )
    }

    private fun clearTasks() {
        userTask.clear()
        nameTask.clear()
        emailTask.clear()
    }

}