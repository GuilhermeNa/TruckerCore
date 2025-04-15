package com.example.truckercore.model.infrastructure.security.authentication.app_errors.error_codes

import com.example.truckercore.model.infrastructure.security.authentication.app_errors.AuthErrorCode

/**
 * Sealed class representing error codes for sign-in operations.
 *
 * Implementations of this sealed class provide:
 * - `name`: A unique identifier for the error.
 * - `userMessage`: A user-friendly message that can be shown to the end user.
 * - `logMessage`: A message that will be logged for internal tracking and debugging.
 * - `isRecoverable`: A boolean indicating if the error is recoverable (i.e., whether the user can attempt the operation again).
 */
sealed class SignInErrCode : AuthErrorCode {

    /**
     * Error code indicating invalid credentials during the sign-in process.
     * This may occur when the provided email or password is incorrect.
     */
    data object InvalidCredentials : SignInErrCode() {
        override val name = "INVALID_CREDENTIALS"
        override val userMessage = "Email ou senha inválidos."
        override val logMessage = "Invalid credentials provided during log in."
        override val isRecoverable = true
    }

    /**
     * Error code indicating a network failure during the sign-in process.
     * This happens when the operation cannot complete due to a network connection issue.
     */
    data object NetworkError : SignInErrCode() {
        override val name = "NETWORK_ERROR"
        override val userMessage = "Erro de conexão. Verifique sua internet e tente novamente."
        override val logMessage = "Network error occurred during login."
        override val isRecoverable = true
    }

    /**
     * Error code indicating too many requests have been made in a short time.
     * This occurs when the system throttles requests due to repeated failed attempts.
     */
    data object TooManyRequests : SignInErrCode() {
        override val name = "TOO_MANY_REQUESTS"
        override val userMessage = "Muitas tentativas. Aguarde um momento e tente novamente."
        override val logMessage = "Too many login attempts. Request was throttled."
        override val isRecoverable = true
    }

    /**
     * Error code indicating an unknown error occurred during the sign-in process.
     * This is a generic error that should be handled when an unforeseen issue arises.
     */
    data object UnknownError : SignInErrCode() {
        override val name = "UNKNOWN_ERROR"
        override val userMessage = "Ocorreu um erro inesperado. Tente novamente mais tarde."
        override val logMessage = "An unknown error occurred during login."
        override val isRecoverable = false
    }

    /**
     * Error code indicating an unsuccessful task during the sign-in process.
     * This could happen due to various reasons that lead to the task failing unexpectedly.
     */
    data object UnsuccessfulTask : SignInErrCode() {
        override val name = "UNSUCCESSFUL_TASK"
        override val userMessage = "Não foi possível concluir o login."
        override val logMessage = "Unexpected failure in the login task."
        override val isRecoverable = true
    }

}