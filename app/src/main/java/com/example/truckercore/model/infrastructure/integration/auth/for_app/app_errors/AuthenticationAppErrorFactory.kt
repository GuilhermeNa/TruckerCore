package com.example.truckercore.model.infrastructure.integration.auth.for_app.app_errors

import com.example.truckercore.model.infrastructure.app_exception.ErrorFactory
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.InvalidCredentialsException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.NetworkException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.SessionInactiveException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.TaskFailureException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.TooManyRequestsException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.UserCollisionException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.WeakPasswordException
import com.example.truckercore.model.infrastructure.integration.auth.for_app.app_errors.error_codes.NewEmailErrCode
import com.example.truckercore.model.infrastructure.integration.auth.for_app.app_errors.error_codes.ObserveEmailValidationErrCode
import com.example.truckercore.model.infrastructure.integration.auth.for_app.app_errors.error_codes.SendEmailVerificationErrCode
import com.example.truckercore.model.infrastructure.integration.auth.for_app.app_errors.error_codes.SignInErrCode
import com.example.truckercore.model.infrastructure.integration.auth.for_app.app_errors.error_codes.UpdateUserProfileErrCode

/**
 * Factory responsible for converting [AuthSourceException] instances into [AuthenticationAppException].
 *
 * This class acts as a centralized error transformation layer between the authentication domain and
 * the application layer. It maps domain-specific exceptions from the authentication module to
 * application-specific exceptions, each carrying a semantic [AppErrorCode] that the presentation layer can handle.
 *
 * Each method corresponds to a specific authentication operation and uses exhaustive `when` branches to
 * ensure appropriate error code assignment for each error type.
 *
 * ### Example usage:
 * ```
 * try {
 *     authSource.createUserWithEmail(email, password)
 * } catch (e: AuthSourceException) {
 *     val appError = appErrorFactory.creatingUserWithEmail(e)
 * }
 * ```
 */
class AuthenticationAppErrorFactory : ErrorFactory {

    /**
     * Maps an exception that occurred while creating a user with email to an [AuthenticationAppException].
     *
     * @param e The domain exception to be mapped.
     * @return An [AuthenticationAppException] with a specific [NewEmailErrCode].
     */
    fun creatingUserWithEmail(e: Exception): AuthenticationAppException {
        val code = when (e) {
            is TaskFailureException -> NewEmailErrCode.TaskFailure
            is WeakPasswordException -> NewEmailErrCode.WeakPassword
            is InvalidCredentialsException -> NewEmailErrCode.InvalidCredentials
            is UserCollisionException -> NewEmailErrCode.AccountCollision
            is NetworkException -> NewEmailErrCode.Network
            else -> NewEmailErrCode.Unknown
        }

        factoryLogger(code)

        return AuthenticationAppException(
            message = code.logMessage,
            cause = e,
            errorCode = code
        )
    }

    /**
     * Maps an exception thrown while sending a verification email.
     *
     * @param e The domain exception to be mapped.
     * @return An [AuthenticationAppException] with a specific [SendEmailVerificationErrCode].
     */
    fun sendingEmailVerification(e: Exception): AuthenticationAppException {
        val code = when (e) {
            is SessionInactiveException -> SendEmailVerificationErrCode.SessionInactive
            is TaskFailureException -> SendEmailVerificationErrCode.TaskFailure
            else -> SendEmailVerificationErrCode.Unknown
        }

        factoryLogger(code)

        return AuthenticationAppException(
            message = code.logMessage,
            cause = e,
            errorCode = code
        )
    }

    /**
     * Maps an exception thrown while updating the user's profile.
     *
     * @param e The domain exception to be mapped.
     * @return An [AuthenticationAppException] with a specific [UpdateUserProfileErrCode].
     */
    fun updatingProfile(e: Exception): AuthenticationAppException {
        val code = when (e) {
            is NetworkException -> UpdateUserProfileErrCode.Network
            is SessionInactiveException -> UpdateUserProfileErrCode.SessionInactive
            is TaskFailureException -> UpdateUserProfileErrCode.TaskFailure
            else -> UpdateUserProfileErrCode.Unknown
        }

        factoryLogger(code)

        return AuthenticationAppException(
            message = code.logMessage,
            cause = e,
            errorCode = code
        )
    }

    /**
     * Maps an exception thrown while observing email verification status.
     *
     * @param e The domain exception to be mapped.
     * @return An [AuthenticationAppException] with a specific [ObserveEmailValidationErrCode].
     */
    fun observingEmailValidation(e: Exception): AuthenticationAppException {
        val code = when (e) {
            is SessionInactiveException -> ObserveEmailValidationErrCode.SessionInactive
            else -> ObserveEmailValidationErrCode.Unknown
        }

        factoryLogger(code)

        return AuthenticationAppException(
            message = code.logMessage,
            errorCode = code,
            cause = e
        )
    }

    /**
     * Maps an exception thrown while signing in the user.
     *
     * @param e The domain exception to be mapped.
     * @return An [AuthenticationAppException] with a specific [SignInErrCode].
     */
    fun signingIn(e: Exception): AuthenticationAppException {
        val code = when (e) {
            is InvalidCredentialsException -> SignInErrCode.InvalidCredentials
            is NetworkException -> SignInErrCode.NetworkError
            is TooManyRequestsException -> SignInErrCode.TooManyRequests
            is TaskFailureException -> SignInErrCode.TaskFailure
            else -> SignInErrCode.UnknownError
        }

        factoryLogger(code)

        return AuthenticationAppException(
            message = code.logMessage,
            cause = e,
            errorCode = code
        )
    }

}


