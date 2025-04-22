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

class AuthenticationAppErrorFactory : ErrorFactory {

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


