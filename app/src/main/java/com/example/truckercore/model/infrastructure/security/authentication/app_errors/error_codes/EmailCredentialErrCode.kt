package com.example.truckercore.model.infrastructure.security.authentication.app_errors.error_codes

import com.example.truckercore.model.infrastructure.security.authentication.app_errors.AuthErrorCode

/**
 * Sealed class representing domain-specific error codes related to user-provided credentials
 * during the account creation or sign-in process (such as name, email, or password).
 *
 * Each subclass defines:
 * - `name`: A unique identifier for the error.
 * - `userMessage`: A friendly message shown to the user.
 * - `logMessage`: A technical message intended for internal logging and debugging.
 * - `isRecoverable`: Indicates whether the error can be corrected by user action.
 */
sealed class EmailCredentialErrCode: AuthErrorCode {

    /**
     * Error indicating that the provided name is invalid or empty.
     * This typically happens when the user submits a blank name or one that doesn't meet validation criteria.
     */
    data object InvalidName : EmailCredentialErrCode() {
        override val name = "INVALID_NAME"
        override val userMessage = "Por favor, digite um nome válido."
        override val logMessage = "Invalid user name provided during credential validation."
        override val isRecoverable = true
    }

    /**
     * Error indicating that the email format is invalid.
     * This occurs when the input does not match the expected pattern for valid email addresses.
     */
    data object InvalidEmail : EmailCredentialErrCode() {
        override val name = "INVALID_EMAIL"
        override val userMessage = "O email fornecido é inválido"
        override val logMessage = "Invalid email format detected during credential validation."
        override val isRecoverable = true
    }

    /**
     * Error indicating that the provided password is invalid.
     * This can happen if the password is too short, too weak, or fails custom password policy checks.
     */
    data object InvalidPassword : EmailCredentialErrCode() {
        override val name = "INVALID_PASSWORD"
        override val userMessage = "A senha fornecida é inválida."
        override val logMessage = "Password validation failed due to weak or invalid input."
        override val isRecoverable = true
    }

    /**
     * Error code indicating an unknown error occurred during the process of creating an email credential.
     */
    data object Unknown : EmailCredentialErrCode() {
        override val name = "UNKNOWN_ERROR"
        override val userMessage = "Ocorreu um erro desconhecido. Tente novamente mais tarde."
        override val logMessage = "Unexpected error during the creation of an email credential."
        override val isRecoverable = false
    }

}




