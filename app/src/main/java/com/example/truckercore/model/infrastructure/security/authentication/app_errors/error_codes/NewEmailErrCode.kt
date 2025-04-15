package com.example.truckercore.model.infrastructure.security.authentication.app_errors.error_codes

import com.example.truckercore.model.infrastructure.security.authentication.app_errors.AuthErrorCode

/**
 * Sealed class representing error codes related to creating a new user account with email and password.
 *
 * Implementations of this sealed class provide:
 * - `name`: A unique identifier for the error.
 * - `userMessage`: A user-friendly message that can be shown to the end user.
 * - `logMessage`: A message that will be logged for internal tracking and debugging.
 * - `isRecoverable`: A boolean indicating if the error is recoverable (i.e., whether the user can attempt the operation again).
 */
sealed class NewEmailErrCode : AuthErrorCode {

    /**
     * Error code indicating that the provided email or password is invalid.
     * This may happen due to malformed email syntax or a password that doesn't meet the security requirements.
     */
    data object InvalidCredentials : NewEmailErrCode() {
        override val name = "INVALID_CREDENTIALS"
        override val userMessage = "E-mail e/ou senha inválidos."
        override val logMessage = "Invalid credentials during creating a new account"
        override val isRecoverable = true
    }

    /**
     * Error code indicating that the email address is already associated with another user account.
     * This typically means the user has already registered using this email.
     */
    data object AccountCollision : NewEmailErrCode() {
        override val name = "ACCOUNT_COLLISION"
        override val userMessage = "Já existe uma conta com este e-mail."
        override val logMessage = "Email already registered"
        override val isRecoverable = true
    }

    /**
     * Error code indicating a network issue occurred while attempting to create the account.
     * This may happen if the device is offline or has poor internet connectivity.
     */
    data object Network : NewEmailErrCode() {
        override val name = "NETWORK_ERROR"
        override val userMessage = "Erro de rede. Verifique sua conexão."
        override val logMessage = "Network failure on creating a new account"
        override val isRecoverable = true
    }

    /**
     * Error code indicating an unknown failure occurred during account creation.
     * This acts as a fallback for unexpected or unclassified errors.
     */
    data object Unknown : NewEmailErrCode() {
        override val name = "UNKNOWN_ERROR"
        override val userMessage = "Erro desconhecido. Tente novamente."
        override val logMessage = "Unexpected error on creating a new account"
        override val isRecoverable = false
    }

    /**
     * Error code indicating an unsuccessful task during the new email creation.
     */
    data object UnsuccessfulTask : NewEmailErrCode() {
        override val name = "UNSUCCESSFUL_TASK"
        override val userMessage = "Não foi possível logar automaticamente. Faça o login."
        override val logMessage = "Unexpected failure in the new email task. Current user cannot be recovered."
        override val isRecoverable = true
    }

}