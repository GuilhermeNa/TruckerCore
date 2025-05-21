package com.example.truckercore.model.infrastructure.integration.auth.for_app.repository

import com.example.truckercore.model.errors.infra.error_code.AuthErrorCode
import com.example.truckercore.model.errors.infra.InfraException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.AuthSourceException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.InvalidCredentialsException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.InvalidUserException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.NetworkException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.SessionInactiveException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.TaskFailureException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.TooManyRequestsException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.UserCollisionException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.WeakPasswordException


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
class AuthRepositoryErrorFactory {

    fun creatingUser(e: Throwable): InfraException {
        if (e is NetworkException) return InfraException.NetworkUnavailable()

        val infraErrCode = when (e) {
            is TaskFailureException -> AuthErrorCode.CreateUserWithEmail.TaskFailure
            is WeakPasswordException -> AuthErrorCode.CreateUserWithEmail.WeakPassword
            is InvalidCredentialsException -> AuthErrorCode.CreateUserWithEmail.InvalidCredential
            is UserCollisionException -> AuthErrorCode.CreateUserWithEmail.UserCollision
            else -> AuthErrorCode.CreateUserWithEmail.Unknown
        }

        return InfraException.AuthError(
            code = infraErrCode,
            message = "Authentication Repository received an error from AuthSource while creating a new User.",
            cause = e
        )
    }

    fun verifyingEmail(e: Throwable): InfraException {
        if (e is NetworkException) return InfraException.NetworkUnavailable()

        val infraErrCode = when (e) {
            is SessionInactiveException -> AuthErrorCode.SessionInactive
            is TaskFailureException -> AuthErrorCode.EmailVerification.TaskFailure
            else -> AuthErrorCode.EmailVerification.Unknown
        }

        return InfraException.AuthError(
            code = infraErrCode,
            message = "Authentication Repository received an error from AuthSource while verifying email.",
            cause = e
        )
    }

    fun signingIn(e: Throwable): InfraException {
        if (e is NetworkException) return InfraException.NetworkUnavailable()

        val infraErrCode = when (e) {
            is InvalidUserException -> AuthErrorCode.SignInWithEmail.InvalidUser
            is InvalidCredentialsException -> AuthErrorCode.SignInWithEmail.InvalidCredential
            is TooManyRequestsException -> AuthErrorCode.SignInWithEmail.TooManyRequests
            is TaskFailureException -> AuthErrorCode.SignInWithEmail.TaskFailure
            else -> AuthErrorCode.SignInWithEmail.Unknown
        }

        return InfraException.AuthError(
            code = infraErrCode,
            message = "Authentication Repository received an error from AuthSource while signing in.",
            cause = e
        )
    }

    fun recoveringEmail(e: Throwable): InfraException {
        if (e is NetworkException) return InfraException.NetworkUnavailable()

        val infraErrCode = when (e) {
            is InvalidCredentialsException -> AuthErrorCode.RecoverEmail.InvalidEmail
            is TaskFailureException -> AuthErrorCode.RecoverEmail.TaskFailure
            else -> AuthErrorCode.RecoverEmail.Unknown
        }

        return InfraException.AuthError(
            code = infraErrCode,
            message = "Authentication Repository received an error from AuthSource while sending email recovery.",
            cause = e
        )
    }

    fun observingEmail(e: Throwable): InfraException {
        if (e is NetworkException) return InfraException.NetworkUnavailable()

        val errorCode = when (e) {
            is SessionInactiveException -> AuthErrorCode.SessionInactive
            else -> AuthErrorCode.ObservingEmailValidation
        }

        return InfraException.AuthError(
            code = errorCode,
            message = "Authentication Repository received an error from AuthSource while observing email validation.",
            cause = e
        )
    }

    fun accessLoggedUser(e: Throwable): InfraException =
        InfraException.AuthError(
            code = AuthErrorCode.SessionInactive,
            message = "Authentication Repository received an error from AuthSource trying to access current user.",
            cause = e
        )

}


