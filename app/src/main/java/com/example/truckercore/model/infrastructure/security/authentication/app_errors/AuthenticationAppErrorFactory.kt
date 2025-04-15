package com.example.truckercore.model.infrastructure.security.authentication.app_errors

import com.example.truckercore.model.infrastructure.security.authentication.app_errors.error_codes.EmailCredentialErrCode
import com.example.truckercore.model.infrastructure.security.authentication.exceptions.NullFirebaseUserException
import com.example.truckercore.model.infrastructure.security.authentication.app_errors.error_codes.NewEmailErrCode
import com.example.truckercore.model.infrastructure.security.authentication.app_errors.error_codes.ObserveEmailValidationErrCode
import com.example.truckercore.model.infrastructure.security.authentication.app_errors.error_codes.SendEmailVerificationErrCode
import com.example.truckercore.model.infrastructure.security.authentication.app_errors.error_codes.SignInErrCode
import com.example.truckercore.model.infrastructure.security.authentication.app_errors.error_codes.UpdateUserProfileErrCode
import com.example.truckercore.model.infrastructure.security.authentication.exceptions.InvalidEmailException
import com.example.truckercore.model.infrastructure.security.authentication.exceptions.InvalidNameException
import com.example.truckercore.model.infrastructure.security.authentication.exceptions.InvalidPasswordException
import com.example.truckercore.model.shared.errors._main.ErrorFactory
import com.example.truckercore.model.shared.utils.sealeds.AppResult
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

/**
 * Factory responsible for mapping Firebase authentication exceptions into
 * domain-specific error codes ([AuthErrorCode])
 *
 * Each function transforms a raw [Throwable] into a structured error with:
 * - A specific error code ([AuthErrorCode])
 * - A user-friendly message
 * - A developer-friendly log message
 *
 * The returned error objects are used consistently throughout the application
 * to ensure unified error handling in UI and logging systems.
 */
object AuthenticationAppErrorFactory : ErrorFactory {

    /**
     * Handles exceptions thrown during the user creation process with email and password.
     *
     * Maps known Firebase exceptions to specific [NewEmailErrCode]s.
     *
     * @param e Optional exception thrown by Firebase. If null, assumes task failed silently.
     * @return An [AuthenticationAppException] with the mapped [NewEmailErrCode].
     *
     * Possible error codes:
     * - [NewEmailErrCode.UnsuccessfulTask] - When the user can not be recovered after the task
     * - [NewEmailErrCode.InvalidCredentials] – Invalid email format or weak password
     * - [NewEmailErrCode.AccountCollision] – Email already in use
     * - [NewEmailErrCode.Network] – Network failure
     * - [NewEmailErrCode.Unknown] – Unexpected error
     */
    fun handleCreateUserWithEmailError(e: Throwable? = null): AuthenticationAppException {
        val code = when (e) {
            is FirebaseAuthWeakPasswordException -> NewEmailErrCode.InvalidCredentials
            is FirebaseAuthInvalidCredentialsException -> NewEmailErrCode.InvalidCredentials
            is FirebaseAuthUserCollisionException -> NewEmailErrCode.AccountCollision
            is FirebaseNetworkException -> NewEmailErrCode.Network
            null -> NewEmailErrCode.UnsuccessfulTask
            else -> NewEmailErrCode.Unknown
        }

        factoryLogger(code)

        return AuthenticationAppException(
            message = code.userMessage,
            cause = e,
            errorCode = code
        )
    }

    /**
     * Handles exceptions that occur when attempting to send a verification email.
     *
     * @param e Optional exception thrown by Firebase. If null, assumes task failed silently.
     * @return An [AuthenticationAppException] with [SendEmailVerificationErrCode].
     *
     * Possible error codes:
     * - [SendEmailVerificationErrCode.UserNotFound]
     * - [SendEmailVerificationErrCode.UnsuccessfulTask]
     * - [SendEmailVerificationErrCode.Unknown]
     */
    fun handleSendEmailVerificationError(e: Throwable? = null): AuthenticationAppException {
        val code = when (e) {
            is NullFirebaseUserException -> SendEmailVerificationErrCode.UserNotFound
            null -> SendEmailVerificationErrCode.UnsuccessfulTask
            else -> SendEmailVerificationErrCode.Unknown
        }

        factoryLogger(code)

        return AuthenticationAppException(
            message = code.userMessage,
            cause = e,
            errorCode = code
        )

    }

    /**
     * Handles exceptions thrown during the user profile update process (e.g., updating name or photo).
     *
     * @param e Optional exception thrown by Firebase. If null, assumes task failed silently.
     *
     * @return An [AuthenticationAppException] with [SendEmailVerificationErrCode].
     *
     * Possible error codes:
     * - [UpdateUserProfileErrCode.Network] – Network failure
     * - [UpdateUserProfileErrCode.UserNotFound] – There is no user in session
     * - [UpdateUserProfileErrCode.UnsuccessfulTask] – Task failed unexpectedly without throwing a specific exception
     * - [UpdateUserProfileErrCode.Unknown] – Unexpected error occurred
     */
    fun handleUpdateUserNameError(e: Throwable? = null): AuthenticationAppException {
        val code = when (e) {
            is FirebaseNetworkException -> UpdateUserProfileErrCode.Network
            is NullFirebaseUserException -> UpdateUserProfileErrCode.UserNotFound
            null -> UpdateUserProfileErrCode.UnsuccessfulTask
            else -> UpdateUserProfileErrCode.Unknown
        }

        factoryLogger(code)

        return AuthenticationAppException(
            message = code.userMessage,
            cause = e,
            errorCode = code
        )

    }

    /**
     * Handles failures that occur when observing the email verification status.
     *
     * This usually happens when the current user is null or the session has expired.
     *
     * @return An [AuthenticationAppException] with [ObserveEmailValidationErrCode].
     */
    fun handleObservingEmailValidationError(): AuthenticationAppException {
        val code = ObserveEmailValidationErrCode.UserNotFound

        factoryLogger(code)

        return AuthenticationAppException(
            message = code.userMessage,
            errorCode = code
        )

    }

    /**
     * Handles errors during user sign-in with email and password.
     *
     * Maps known Firebase sign-in exceptions to domain-specific [SignInErrCode] values.
     *
     * @param e Optional exception from FirebaseAuth. If null, assumes the sign-in task failed silently.
     * @return A [AppResult.Error] containing an [AuthenticationAppException] with the corresponding [SignInErrCode].
     *
     * Possible error codes:
     * - [SignInErrCode.InvalidCredentials] – Invalid email or password
     * - [SignInErrCode.NetworkError] – No internet connection
     * - [SignInErrCode.TooManyRequests] – Rate-limited by Firebase
     * - [SignInErrCode.UnsuccessfulTask] – Task failed without exception
     * - [SignInErrCode.UnknownError] – Unexpected error
     */
    fun handleSignInError(e: Throwable? = null): AuthenticationAppException {
        val code = when (e) {
            is FirebaseAuthInvalidUserException -> SignInErrCode.InvalidCredentials
            is FirebaseAuthInvalidCredentialsException -> SignInErrCode.InvalidCredentials
            is FirebaseNetworkException -> SignInErrCode.NetworkError
            is FirebaseTooManyRequestsException -> SignInErrCode.TooManyRequests
            null -> SignInErrCode.UnsuccessfulTask
            else -> SignInErrCode.UnknownError
        }

        factoryLogger(code)

        return AuthenticationAppException(
            message = code.userMessage,
            cause = e,
            errorCode = code
        )

    }

    /**
     * Handles validation errors related to user-provided credentials such as name, email, and password.
     *
     * This function maps known input validation exceptions to domain-specific [EmailCredentialErrCode] values,
     * allowing consistent error reporting and handling in both UI and logging systems.
     *
     * @param e Optional exception thrown during credential validation. If unknown, maps to [EmailCredentialErrCode.Unknown].
     * @return An [AuthenticationAppException] wrapping the appropriate [EmailCredentialErrCode].
     *
     * Possible error codes:
     * - [EmailCredentialErrCode.InvalidName] – Provided name is empty or invalid
     * - [EmailCredentialErrCode.InvalidEmail] – Email format is invalid
     * - [EmailCredentialErrCode.InvalidPassword] – Password is too weak or invalid
     * - [EmailCredentialErrCode.Unknown] – Any unexpected or unmapped error
     */
    fun handleEmailCredentialError(e: Throwable? = null): AuthenticationAppException {
        val code = when (e) {
            is InvalidNameException -> EmailCredentialErrCode.InvalidName
            is InvalidEmailException -> EmailCredentialErrCode.InvalidEmail
            is InvalidPasswordException -> EmailCredentialErrCode.InvalidPassword
            else -> EmailCredentialErrCode.Unknown
        }

        factoryLogger(code)

        return AuthenticationAppException(
            message = code.userMessage,
            cause = e,
            errorCode = code
        )
    }

}


