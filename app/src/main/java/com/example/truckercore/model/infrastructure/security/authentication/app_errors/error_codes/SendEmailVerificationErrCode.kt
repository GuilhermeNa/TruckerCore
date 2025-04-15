package com.example.truckercore.model.infrastructure.security.authentication.app_errors.error_codes

import com.example.truckercore.model.infrastructure.security.authentication.app_errors.AuthErrorCode

/**
 * Sealed class representing error codes related to sending email verification messages.
 *
 * Implementations of this sealed class provide:
 * - `name`: A unique identifier for the error.
 * - `userMessage`: A user-friendly message that can be shown to the end user.
 * - `logMessage`: A message that will be logged for internal tracking and debugging.
 * - `isRecoverable`: A boolean indicating if the error is recoverable (i.e., whether the user can attempt the operation again).
 */
sealed class SendEmailVerificationErrCode : AuthErrorCode {

    /**
     * Error code indicating that the task for sending the verification email failed unexpectedly.
     * This may happen due to internal issues such as failure in Firebase's task completion.
     */
    data object UnsuccessfulTask : SendEmailVerificationErrCode() {
        override val name = "UNSUCCESSFUL_TASK"
        override val userMessage = "Não foi possível concluir o envio do e-mail de verificação."
        override val logMessage = "Unexpected failure in the email verification task."
        override val isRecoverable = true
    }

    /**
     * Error code indicating an unknown error occurred during the process of sending the verification email.
     * This is a generic fallback for unhandled or unexpected exceptions.
     */
    data object Unknown : SendEmailVerificationErrCode() {
        override val name = "UNKNOWN_ERROR"
        override val userMessage = "Ocorreu um erro desconhecido. Tente novamente mais tarde."
        override val logMessage = "Unexpected error during the email verification process."
        override val isRecoverable = false
    }

    /**
     * Error code indicating that no user is currently authenticated or the session has expired
     * while attempting to send email verification.
     */
    data object UserNotFound : SendEmailVerificationErrCode() {
        override val name = "USER_NOT_FOUND"
        override val userMessage = "Usuário não encontrado. Tente fazer o login."
        override val logMessage = "Send email attempt failed. User not found or session expired "
        override val isRecoverable = false
    }

}