package com.example.truckercore.model.infrastructure.integration._auth.use_cases.implementations

import com.example.truckercore.model.configs.constants.EarlyExit
import com.example.truckercore.model.infrastructure.integration._auth.entity.EmailCredential
import com.example.truckercore.model.infrastructure.security.authentication.entity.NewEmailResult
import com.example.truckercore.model.infrastructure.integration._auth.repository.AuthenticationRepository
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.interfaces.CreateUserAndVerifyEmailUseCase
import com.example.truckercore.model.infrastructure.utils.task_manager.TaskManager
import com.example.truckercore.model.shared.utils.expressions.handleAppResult
import com.example.truckercore.model.shared.utils.expressions.logError
import com.example.truckercore.model.shared.utils.expressions.mapAppResult
import com.example.truckercore.model.shared.utils.expressions.onEarlyExit
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll

internal class CreateUserAndVerifyEmailUseCaseImpl(
    private val authRepository: com.example.truckercore.model.infrastructure.integration._auth.repository.AuthenticationRepository,
    private val userTask: TaskManager<FirebaseUser>,
    private val nameTask: TaskManager<Unit>,
    private val emailTask: TaskManager<Unit>,
) : CreateUserAndVerifyEmailUseCase {

    override suspend fun invoke(credential: com.example.truckercore.model.infrastructure.integration._auth.entity.EmailCredential): NewEmailResult {
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

    private suspend fun createUser(credential: com.example.truckercore.model.infrastructure.integration._auth.entity.EmailCredential): EarlyExit {
        return authRepository.createUserWithEmail(credential.email.value, credential.password.value)
            .mapAppResult(
                success = { fbUser ->
                    // Mark the task as successful with the Firebase user
                    userTask.onSuccess(fbUser)
                    false // No early exit
                },
                error = { e ->
                    // Handle error during user creation
                    userTask.onError(e)
                    true // Early exit
                }
            )
    }

    private suspend fun updateNameAndSendEmailAsync(credential: com.example.truckercore.model.infrastructure.integration._auth.entity.EmailCredential): Unit =
        coroutineScope {
            val nameDef = async { updateName(credential) }
            val emailDef = async { sendEmail() }
            joinAll(nameDef, emailDef)
        }

    private suspend fun updateName(credential: com.example.truckercore.model.infrastructure.integration._auth.entity.EmailCredential) {
        val request = userProfileChangeRequest { displayName = credential.name.value }

        authRepository.updateUserProfile(request).handleAppResult(
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
            firebaseUser = userTask.result,
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