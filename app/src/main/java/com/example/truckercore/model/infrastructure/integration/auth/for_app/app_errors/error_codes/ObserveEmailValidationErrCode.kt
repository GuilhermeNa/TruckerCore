package com.example.truckercore.model.infrastructure.integration.auth.for_app.app_errors.error_codes

import com.example.truckercore.model.infrastructure.integration.auth.for_app.app_errors.AuthErrorCode

/**
 * Sealed class representing error codes related to observing the email validation status.
 *
 * Implementations of this sealed class provide:
 * - `name`: A unique identifier for the error.
 * - `userMessage`: A user-friendly message that can be shown to the end user.
 * - `logMessage`: A message that will be logged for internal tracking and debugging.
 * - `isRecoverable`: A boolean indicating if the error is recoverable (i.e., whether the user can attempt the operation again).
 */
sealed class ObserveEmailValidationErrCode : AuthErrorCode {

    /**
     * Error code indicating that no user is currently authenticated or the session has expired
     * while attempting to observe the email verification status.
     * This usually happens when trying to reload the user state after logout or session timeout.
     */
    data object SessionInactive : ObserveEmailValidationErrCode() {
        override val name = "SESSION_INACTIVE"
        override val userMessage = "Usuário não encontrado. Tente fazer o login."
        override val logMessage =
            "Email validation attempt failed. User not found or session expired."
        override val isRecoverable = false
    }

    data object Unknown : ObserveEmailValidationErrCode() {
        override val name = "UNKNOWN_ERROR"
        override val userMessage = "Erro desconhecido. Tente novamente."
        override val logMessage = "Unexpected error on updating user profile"
        override val isRecoverable = false
    }

}