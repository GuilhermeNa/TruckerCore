package com.example.truckercore.model.infrastructure.security.authentication.errors

import com.example.truckercore.model.shared.errors._main.ErrorFactory
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import com.example.truckercore.model.shared.utils.sealeds.AppResult
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

/**
 * Factory responsible for mapping Firebase authentication exceptions into
 * domain-specific error codes ([AuthErrorCode]) and wrapping them in standardized result types
 * such as [AppResponse.Error] and [AppResult.Error].
 *
 * Each function transforms a raw [Throwable] into a structured error with:
 * - A specific error code ([AuthErrorCode])
 * - A user-friendly message
 * - A developer-friendly log message
 *
 * The returned error objects are used consistently throughout the application
 * to ensure unified error handling in UI and logging systems.
 */
object AuthErrorFactory : ErrorFactory {

    /**
     * Handles exceptions thrown during the user creation process with email and password.
     *
     * Maps known Firebase exceptions to specific [NewEmailErrCode]s and wraps them in a [AppResponse.Error].
     *
     * @param e The exception thrown by FirebaseAuth.
     * @return A [AppResponse.Error] containing an [AuthenticationException] with the mapped [NewEmailErrCode].
     *
     * Possible error codes:
     * - [NewEmailErrCode.InvalidCredentials] – Invalid email format or weak password
     * - [NewEmailErrCode.AccountCollision] – Email already in use
     * - [NewEmailErrCode.Network] – Network failure
     * - [NewEmailErrCode.Unknown] – Unexpected error
     */
    fun handleCreateUserWithEmailError(e: Throwable): AppResponse.Error {
        val code = when (e) {
            is FirebaseAuthWeakPasswordException -> NewEmailErrCode.InvalidCredentials
            is FirebaseAuthInvalidCredentialsException -> NewEmailErrCode.InvalidCredentials
            is FirebaseAuthUserCollisionException -> NewEmailErrCode.AccountCollision
            is FirebaseNetworkException -> NewEmailErrCode.Network
            else -> NewEmailErrCode.Unknown
        }

        factoryLogger(code)

        val mError = AuthenticationException(
            message = code.userMessage,
            cause = e,
            errorCode = code
        )

        return AppResponse.Error(mError)
    }

    /**
     * Handles exceptions that occur when attempting to send a verification email.
     *
     * @param e Optional exception thrown by Firebase. If null, assumes task failed silently.
     * @return A [AppResult.Error] containing an [AuthenticationException] with [SendEmailVerificationErrCode].
     *
     * Possible error codes:
     * - [SendEmailVerificationErrCode.UnsuccessfulTask]
     * - [SendEmailVerificationErrCode.Unknown]
     */
    fun handleSendEmailVerificationError(e: Throwable? = null): AppResult.Error {
        val code = when (e) {
            null -> SendEmailVerificationErrCode.UnsuccessfulTask
            else -> SendEmailVerificationErrCode.Unknown
        }

        factoryLogger(code)

        val mError = AuthenticationException(
            message = code.userMessage,
            cause = e,
            errorCode = code
        )

        return AppResult.Error(mError)
    }

    /**
     * Handles exceptions thrown during the user profile update process (e.g., updating name or photo).
     *
     * Maps known Firebase exceptions to specific error codes and wraps them in a [AppResult.Error].
     *
     * @param e The exception thrown by FirebaseAuth during the profile update task.
     *          If null, assumes the task failed without throwing a specific exception.
     * @return A [AppResult.Error] containing an [AuthenticationException] with the mapped error code.
     *
     * Possible error codes:
     * - [SendEmailVerificationErrCode.UnsuccessfulTask] – Task failed unexpectedly without throwing a specific exception
     * - [SendEmailVerificationErrCode.Unknown] – Unexpected error occurred
     */
    fun handleUpdateUserNameError(e: Throwable? = null): AppResult.Error {
        val code = when (e) {
            null -> SendEmailVerificationErrCode.UnsuccessfulTask
            else -> SendEmailVerificationErrCode.Unknown
        }

        factoryLogger(code)

        val mError = AuthenticationException(
            message = code.userMessage,
            cause = e,
            errorCode = code
        )

        return AppResult.Error(mError)
    }

    /**
     * Handles failures that occur when observing the email verification status.
     *
     * This usually happens when the current user is null or the session has expired.
     *
     * @return A [AppResponse.Error] containing [ObserveEmailValidationErrCode.UserNotFound].
     */
    fun handleObservingEmailValidationError(): AppResponse.Error {
        val code = ObserveEmailValidationErrCode.UserNotFound

        factoryLogger(code)

        val mError = AuthenticationException(
            message = code.userMessage,
            errorCode = code
        )

        return AppResponse.Error(mError)
    }

    /**
     * Handles errors during user sign-in with email and password.
     *
     * Maps known Firebase sign-in exceptions to domain-specific [SignInErrCode] values.
     *
     * @param e Optional exception from FirebaseAuth. If null, assumes the sign-in task failed silently.
     * @return A [AppResult.Error] containing an [AuthenticationException] with the corresponding [SignInErrCode].
     *
     * Possible error codes:
     * - [SignInErrCode.InvalidCredentials] – Invalid email or password
     * - [SignInErrCode.NetworkError] – No internet connection
     * - [SignInErrCode.TooManyRequests] – Rate-limited by Firebase
     * - [SignInErrCode.UnsuccessfulTask] – Task failed without exception
     * - [SignInErrCode.UnknownError] – Unexpected error
     */
    fun handleSignInError(e: Throwable? = null): AppResult.Error {
        val code = when (e) {
            is FirebaseAuthInvalidUserException -> SignInErrCode.InvalidCredentials
            is FirebaseAuthInvalidCredentialsException -> SignInErrCode.InvalidCredentials
            is FirebaseNetworkException -> SignInErrCode.NetworkError
            is FirebaseTooManyRequestsException -> SignInErrCode.TooManyRequests
            null -> SignInErrCode.UnsuccessfulTask
            else -> SignInErrCode.UnknownError
        }

        factoryLogger(code)

        val mError = AuthenticationException(
            message = code.userMessage,
            cause = e,
            errorCode = code
        )

        return AppResult.Error(mError)
    }

}


