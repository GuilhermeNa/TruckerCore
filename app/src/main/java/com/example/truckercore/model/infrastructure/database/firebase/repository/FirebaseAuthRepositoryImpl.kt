package com.example.truckercore.model.infrastructure.database.firebase.repository

import com.example.truckercore.model.infrastructure.database.firebase.errors.IncompleteTaskException
import com.example.truckercore.model.infrastructure.security.authentication.errors.NewEmailErrCode
import com.example.truckercore.model.infrastructure.security.authentication.errors.NewEmailUserException
import com.example.truckercore.model.infrastructure.security.authentication.errors.NullFirebaseUserException
import com.example.truckercore.model.infrastructure.security.authentication.errors.SendEmailVerificationErrCode
import com.example.truckercore.model.infrastructure.security.authentication.errors.SendEmailVerificationException
import com.example.truckercore.model.infrastructure.security.authentication.errors.UpdateProfileErrCode
import com.example.truckercore.model.infrastructure.security.authentication.errors.UpdateUserProfileException
import com.example.truckercore.model.shared.utils.expressions.logError
import com.example.truckercore.model.shared.utils.expressions.logInfo
import com.example.truckercore.model.shared.utils.expressions.logWar
import com.example.truckercore.model.shared.utils.sealeds.Response
import com.example.truckercore.model.shared.utils.sealeds.Result
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal class FirebaseAuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : FirebaseAuthRepository {

    private val eh = ErrorHandler()

    override suspend fun createUserWithEmail(
        email: String, password: String
    ): Response<FirebaseUser> = suspendCoroutine { continuation ->
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            task.exception?.let { e ->
                logError("Error creating new user with email: $email, error: $e")
                val errorResp = eh.handleCreateUserWithEmailError(e)
                continuation.resume(errorResp)
                return@addOnCompleteListener
            }

            val response = task.result.user?.let { fbUser ->
                Response.Success(fbUser)
            } ?: Response.Empty

            if (response is Response.Success) {
                logInfo("User created successfully with ID: ${task.result.user?.uid}.")
            } else {
                logWar("User account creation returned an unexpected Response: Empty.")
            }

            continuation.resume(response)
        }
    }

    override suspend fun sendEmailVerification(
        firebaseUser: FirebaseUser
    ): Result<Unit> = suspendCoroutine { continuation ->
        firebaseUser.sendEmailVerification().addOnCompleteListener { task ->
            task.exception?.let { e ->
                logError("Error sending email verification: ${firebaseUser.email}, error: $e.")
                val errorRes = eh.handleSendEmailVerificationError(e)
                continuation.resume(errorRes)
                return@addOnCompleteListener
            }

            val response = if (task.isSuccessful) Result.Success(Unit)
            else eh.getErrorForSendEmailUnsuccessfulTask()

            if (response is Result.Success) {
                logInfo("Email verification sent: ${firebaseUser.email}.")
            } else {
                logError("Email verification failed due unsuccessful task: ${firebaseUser.email}.")
            }

            continuation.resume(response)
        }
    }

    override suspend fun updateUserProfile(
        fbUser: FirebaseUser,
        profile: UserProfileChangeRequest
    ): Result<Unit> = suspendCoroutine { continuation ->
        fbUser.updateProfile(profile).addOnCompleteListener { task ->
            task.exception?.let { e ->
                logError("Error updating profile: ${fbUser.email}, error: $e.")
                val errorResp = eh.handleUpdateUserNameError(e)
                continuation.resume(errorResp)
                return@addOnCompleteListener
            }

            val response = if (task.isSuccessful) Result.Success(Unit)
            else eh.getErrorForUpdateUserProfileUnsuccessfulTask()

            if (response is Result.Success) {
                logInfo("Profile updated: ${fbUser.email}.")
            } else {
                logError("Profile update failed due unsuccessful task: ${fbUser.email}.")
            }

            continuation.resume(response)
        }
    }

    // Helper Class --------------------------------------------------------------------------------

    private class ErrorHandler {

        companion object {
            // Mensagens de erro para criação de usuário
            const val CREATE_USER_EMAIL_NETWORK_ERROR =
                "Network error while creating your email account. Please check your internet connection."
            const val CREATE_USER_EMAIL_ACCOUNT_COLLISION =
                "An account is already registered with this email. Please use a different email."
            const val CREATE_USER_EMAIL_INVALID_CREDENTIALS =
                "Invalid email and/or password. Please check your credentials."
            const val CREATE_USER_EMAIL_UNKNOWN_ERROR =
                "An unknown error occurred while creating your email account. Please try again later."

            // Mensagens de erro para envio de verificação de email
            const val SEND_EMAIL_VERIFICATION_NETWORK_ERROR =
                "Network error while sending email verification. Please check your internet connection."
            const val SEND_EMAIL_VERIFICATION_EMAIL_NOT_FOUND =
                "The email address was not found. It is not possible send verification code."
            const val SEND_EMAIL_VERIFICATION_UNKNOWN_ERROR =
                "An unknown error occurred while trying to send the email verification. Please try again later."

            // Mensagens de erro para atualização do nome de usuário
            const val UPDATE_USER_PROFILE_NETWORK_ERROR =
                "Network error while updating your profile. Please check your internet connection."
            const val UPDATE_USER_PROFILE_UNKNOWN_ERROR =
                "An unknown error occurred while updating your profile. Please try again later."

            // Mensagens de erro gerais
            const val ERROR_SEND_EMAIL_UNSUCCESSFUL_TASK =
                "Failed on recover a successful task while sending an verification email."
            const val ERROR_UPDATE_USER_PROFILE_UNSUCCESSFUL_TASK =
                "Failed on recover a successful task while updating an user name."
        }

        fun handleCreateUserWithEmailError(e: Exception): Response<FirebaseUser> {
            // Mapping exception to error code and user-friendly message
            val (code, message) = when (e) {
                is FirebaseNetworkException -> NewEmailErrCode.Network() to CREATE_USER_EMAIL_NETWORK_ERROR
                is FirebaseAuthUserCollisionException -> NewEmailErrCode.AccountCollision() to CREATE_USER_EMAIL_ACCOUNT_COLLISION
                is FirebaseAuthInvalidCredentialsException -> NewEmailErrCode.InvalidCredentials() to CREATE_USER_EMAIL_INVALID_CREDENTIALS
                else -> NewEmailErrCode.Unknown() to CREATE_USER_EMAIL_UNKNOWN_ERROR
            }

            // Returning the response with the exception, error code, and message
            return Response.Error(NewEmailUserException(message = message, cause = e, code = code))
        }

        fun handleSendEmailVerificationError(e: Exception): Result.Error {
            // Mapping the exception to the error code and user-friendly message
            val (code, message) = when (e) {
                is FirebaseNetworkException -> {
                    SendEmailVerificationErrCode.Network() to SEND_EMAIL_VERIFICATION_NETWORK_ERROR
                }

                is FirebaseAuthInvalidUserException -> {
                    SendEmailVerificationErrCode.EmailNotFound() to SEND_EMAIL_VERIFICATION_EMAIL_NOT_FOUND
                }

                else -> {
                    SendEmailVerificationErrCode.Unknown() to SEND_EMAIL_VERIFICATION_UNKNOWN_ERROR
                }
            }

            // Returning the response with the exception, error code, and message
            return Result.Error(
                SendEmailVerificationException(
                    message = message,
                    cause = e,
                    code = code
                )
            )
        }

        fun getErrorForSendEmailUnsuccessfulTask(): Result.Error {
            return Result.Error(
                SendEmailVerificationException(
                    message = ERROR_SEND_EMAIL_UNSUCCESSFUL_TASK,
                    code = SendEmailVerificationErrCode.UnsuccessfulTask()
                )
            )
        }

        fun handleUpdateUserNameError(e: Exception): Result.Error {
            // Mapping exception to error code and user-friendly message
            val (code, message) = when (e) {
                is FirebaseNetworkException ->
                    UpdateProfileErrCode.Network() to UPDATE_USER_PROFILE_NETWORK_ERROR

                else ->
                    UpdateProfileErrCode.Unknown() to UPDATE_USER_PROFILE_UNKNOWN_ERROR

            }

            // Returning the response with the exception, error code, and message
            return Result.Error(
                UpdateUserProfileException(
                    message = message,
                    cause = e,
                    code = code
                )
            )
        }

        fun getErrorForUpdateUserProfileUnsuccessfulTask(): Result.Error {
            return Result.Error(
                UpdateUserProfileException(
                    message = ERROR_UPDATE_USER_PROFILE_UNSUCCESSFUL_TASK,
                    code = UpdateProfileErrCode.UnsuccessfulTask()
                )
            )
        }

    }

    // TODO("refatorar os metodos abaixo")


    override suspend fun createUserWithPhone(credential: PhoneAuthCredential) =
        suspendCoroutine { cont ->
            firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
                task.exception?.let { error ->
                    cont.resume(Response.Error(error))
                    return@addOnCompleteListener
                }
                task.result.user?.let { user ->
                    cont.resume(Response.Success(user.uid))
                } ?: cont.resumeWithException(IncompleteTaskException(NEW_USER_ERROR_MESSAGE))
            }
        }

    override fun signIn(email: String, password: String) = callbackFlow {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                task.exception?.let { error ->
                    this.close(error)
                }

                if (task.isSuccessful) trySend(Response.Success(Unit))
                else close(IncompleteTaskException("Failure when trying to login."))
            }

        awaitClose { this.cancel() }
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    override fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser

    override fun observeEmailValidation(): Flow<Response<Unit>> = flow {
        while (coroutineContext.isActive) {
            delay(1000)

            val response = getCurrentUser()?.let { fbUser ->
                fbUser.reload()
                if (fbUser.isEmailVerified) Response.Success(Unit)
                else Response.Empty
            } ?: Response.Error(NullFirebaseUserException(FB_USER_NOT_FOUND))

            emit(response)
        }
    }

    companion object {
        private const val NEW_USER_ERROR_MESSAGE = "Task failed on recover new user."
        private const val VERIFICATION_ERROR_MESSAGE =
            "Task failed on send verification to registered email."
        private const val UPDATE_PROFILE_ERROR_MSG = "Task failed on update user profile."
        private const val FB_USER_NOT_FOUND = "The firebase user was not found."
    }

}