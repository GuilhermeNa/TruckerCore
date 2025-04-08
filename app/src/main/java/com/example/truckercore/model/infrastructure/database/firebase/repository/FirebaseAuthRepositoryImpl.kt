package com.example.truckercore.model.infrastructure.database.firebase.repository

import com.example.truckercore.model.infrastructure.database.firebase.errors.IncompleteTaskException
import com.example.truckercore.model.infrastructure.security.authentication.errors.NewEmailUserException
import com.example.truckercore.model.infrastructure.security.authentication.errors.NewEmailUserException.ErrorCode
import com.example.truckercore.model.infrastructure.security.authentication.errors.NullFirebaseUserException
import com.example.truckercore.model.infrastructure.security.authentication.errors.SendEmailVerificationException
import com.example.truckercore.model.infrastructure.security.authentication.errors.UpdateUserProfileException
import com.example.truckercore.model.shared.utils.sealeds.Response
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
    ) = suspendCoroutine { continuation ->
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            task.exception?.let { e ->
                val errorResp = eh.handleCreateUserWithEmailError(e)
                continuation.resume(errorResp)
                return@addOnCompleteListener
            }

            val response = task.result.user?.let { fbUser ->
                Response.Success(fbUser)
            } ?: eh.getErrorForCreateUserWithEmailUserNotFound()

            continuation.resume(response)
        }
    }

    override suspend fun sendEmailVerification(
        firebaseUser: FirebaseUser
    ): Response<Unit> = suspendCoroutine { continuation ->
        firebaseUser.sendEmailVerification().addOnCompleteListener { task ->
            task.exception?.let { e ->
                val errorRes = eh.handleSendEmailVerificationError(e)
                continuation.resume(errorRes)
                return@addOnCompleteListener
            }

            val response = if (task.isSuccessful) Response.Success(Unit)
            else eh.getErrorForSendEmailUnsuccessfulTask()

            continuation.resume(response)
        }
    }

    override suspend fun updateUserProfile(
        fbUser: FirebaseUser,
        profile: UserProfileChangeRequest
    ): Response<Unit> = suspendCoroutine { continuation ->
        fbUser.updateProfile(profile).addOnCompleteListener { task ->
            task.exception?.let { e ->
                val errorResp = eh.handleUpdateUserNameError(e)
                continuation.resume(errorResp)
                return@addOnCompleteListener
            }

            val response = if (task.isSuccessful) Response.Success(Unit)
            else eh.getErrorForUpdateUserProfileUnsuccessfulTask()

            continuation.resume(response)
        }
    }

    private class ErrorHandler {

        fun handleCreateUserWithEmailError(e: Exception): Response<FirebaseUser> {
            // Mapping exception to error code and user-friendly message
            val (code, message) = when (e) {
                is FirebaseNetworkException -> NewEmailUserException.ErrorCode.Network to "Network error while creating your email account. Please check your internet connection."
                is FirebaseAuthUserCollisionException -> NewEmailUserException.ErrorCode.AccountCollision to "An account is already registered with this email. Please use a different email."
                is FirebaseAuthInvalidCredentialsException -> NewEmailUserException.ErrorCode.InvalidCredentials to "Invalid email and/or password. Please check your credentials."
                else -> NewEmailUserException.ErrorCode.Unknown to "An unknown error occurred while creating your email account. Please try again later."
            }

            // Returning the response with the exception, error code, and message
            return Response.Error(NewEmailUserException(message = message, cause = e, code = code))
        }

        fun getErrorForCreateUserWithEmailUserNotFound(): Response.Error {
            val message = "Failed on recover firebase user from task while creating an email."
            return Response.Error(
                NewEmailUserException(
                    message = message,
                    code = ErrorCode.UserNotFound
                )
            )
        }

        fun handleSendEmailVerificationError(e: Exception): Response<Unit> {
            // Mapping the exception to the error code and user-friendly message
            val (code, message) = when (e) {
                is FirebaseNetworkException -> SendEmailVerificationException.ErrorCode.Network to "Network error while sending email verification. Please check your internet connection."
                is FirebaseAuthInvalidUserException -> SendEmailVerificationException.ErrorCode.EmailNotFound to "The email address was not found. It is not possible send verification code."
                else -> SendEmailVerificationException.ErrorCode.Unknown to "An unknown error occurred while trying to send the email verification. Please try again later."
            }

            // Returning the response with the exception, error code, and message
            return Response.Error(
                SendEmailVerificationException(
                    message = message,
                    cause = e,
                    code = code
                )
            )
        }

        fun getErrorForSendEmailUnsuccessfulTask(): Response.Error {
            val message = "Failed on recover a successful task while sending an verification email."
            return Response.Error(
                SendEmailVerificationException(
                    message = message,
                    code = SendEmailVerificationException.ErrorCode.Unknown
                )
            )
        }

        fun handleUpdateUserNameError(e: java.lang.Exception): Response<Unit> {
            // Mapping exception to error code and user-friendly message
            val (code, message) = when (e) {
                is FirebaseNetworkException -> UpdateUserProfileException.ErrorCode.Network to "Network error while updating your profile. Please check your internet connection."
                else -> UpdateUserProfileException.ErrorCode.Unknown to "An unknown error occurred while updating your profile. Please try again later."
            }

            // Returning the response with the exception, error code, and message
            return Response.Error(
                UpdateUserProfileException(
                    message = message,
                    cause = e,
                    code = code
                )
            )
        }

        fun getErrorForUpdateUserProfileUnsuccessfulTask(): Response.Error {
            val message = "Failed on recover a successful task while updating an user name."
            return Response.Error(
                UpdateUserProfileException(
                    message = message,
                    code = UpdateUserProfileException.ErrorCode.UnsuccessfulTask
                )
            )
        }

    }








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