package com.example.truckercore.model.infrastructure.security.authentication.use_cases

import com.example.truckercore.model.infrastructure.database.firebase.repository.FirebaseAuthRepository
import com.example.truckercore.model.infrastructure.security.authentication.entity.EmailAuthCredential
import com.example.truckercore.model.infrastructure.security.authentication.entity.NewEmailResult
import com.example.truckercore.model.infrastructure.security.authentication.errors.NullFirebaseUserException
import com.example.truckercore.model.shared.utils.expressions.logError
import com.example.truckercore.model.shared.utils.expressions.onEarlyExit
import com.example.truckercore.model.configs.app_constants.EarlyExit
import com.example.truckercore.model.shared.task_manager.TaskManager
import com.example.truckercore.model.shared.utils.sealeds.handleResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll

/**
 * Implementation of the [CreateUserAndVerifyEmailUseCase] interface.
 * This class handles the creation of a new user, the updating of their display name, and
 * the sending of an email verification request in Firebase.
 */
internal class CreateUserAndVerifyEmailUseCaseImpl(
    private val authRepository: FirebaseAuthRepository,
    private val userTask: TaskManager<FirebaseUser>,
    private val nameTask: TaskManager<Unit>,
    private val emailTask: TaskManager<Unit>,
) : CreateUserAndVerifyEmailUseCase {

    // Firebase user getter
    private val fbUser get() = userTask.result

    /**
     * Invokes the use case to create a new user and verify their email.
     * It performs the tasks of creating the user, updating the user's display name,
     * and sending the email verification asynchronously.
     *
     * @param credential The credentials (email and password) used to create the user.
     * @return [NewEmailResult] A result containing success or failure information
     *         about the user creation, name update, and email verification.
     */
    override suspend fun invoke(credential: EmailAuthCredential): NewEmailResult {
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

    /**
     * Creates a new user with the provided credentials.
     *
     * @param credential The credentials used to create the user.
     * @return [EarlyExit] A flag indicating whether the process should exit early.
     */
    private suspend fun createUser(credential: EmailAuthCredential): EarlyExit {
        return authRepository.createUserWithEmail(credential.email, credential.password)
            .handleResponseAndConsume(
                success = { fbUser ->
                    // Mark the task as successful with the Firebase user
                    userTask.onSuccess(fbUser)
                    false // No early exit
                },
                empty = {
                    // Handle empty response scenario
                    val exception = NullFirebaseUserException(AUTH_EMAIL_RETURN_EMPTY)
                    userTask.onCancel(exception)
                    true // Early exit
                },
                error = { e ->
                    // Handle error during user creation
                    userTask.onError(e)
                    true // Early exit
                }
            )
    }

    /**
     * Updates the user's display name and sends an email verification concurrently.
     *
     * @param credential The credentials (email and name) used for updating the user's information.
     */
    private suspend fun updateNameAndSendEmailAsync(credential: EmailAuthCredential): Unit =
        coroutineScope {
            val nameDef = async { updateName(credential) }
            val emailDef = async { sendEmail() }
            joinAll(nameDef, emailDef)
        }

    /**
     * Updates the user's display name in Firebase.
     *
     * @param credential The credentials (name) used to update the user's display name.
     */
    private suspend fun updateName(credential: EmailAuthCredential) {
        val request = userProfileChangeRequest { displayName = credential.name }

        fbUser?.let { nonNullFbUser ->
            authRepository.updateUserProfile(nonNullFbUser, request)
                .handleResult(
                    success = { nameTask.onSuccess(Unit) },
                    error = { e -> nameTask.onError(e) }
                )
        } ?: nameTask.onCancel(NullFirebaseUserException(FB_USER_NOT_INITIALIZED))
    }

    /**
     * Sends the email verification to the user.
     */
    private suspend fun sendEmail() {
        fbUser?.let { nonNullFbUser ->
            authRepository.sendEmailVerification(nonNullFbUser)
                .handleResult(
                    success = { emailTask.onSuccess(Unit) },
                    error = { e -> emailTask.onError(e) }
                )
        } ?: emailTask.onCancel(NullFirebaseUserException(FB_USER_NOT_INITIALIZED))
    }

    /**
     * Constructs and returns the final result of the email creation process.
     *
     * @return [NewEmailResult] A result containing the status of user creation, name update,
     *         email verification, and any encountered errors.
     */
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

    /**
     * Clear tasks to reset to the initial state, in case the user registers more emails.
     */
    private fun clearTasks() {
        userTask.clear()
        nameTask.clear()
        emailTask.clear()
    }

    companion object {
        private const val AUTH_EMAIL_RETURN_EMPTY =
            "Firebase user was not found while authenticating an email and returned an Empty Response."
        private const val FB_USER_NOT_INITIALIZED =
            "Firebase user was not initialized correctly."
    }

}