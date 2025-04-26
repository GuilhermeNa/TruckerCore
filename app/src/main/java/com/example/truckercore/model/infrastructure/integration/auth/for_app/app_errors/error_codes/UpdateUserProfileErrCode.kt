package com.example.truckercore.model.infrastructure.integration.auth.for_app.app_errors.error_codes

import com.example.truckercore.model.infrastructure.integration.auth.for_app.app_errors.AuthErrorCode

/**
 * Sealed class representing error codes for user profile update operations.
 *
 * Implementations of this sealed class provide:
 * - `name`: A unique identifier for the error.
 * - `userMessage`: A user-friendly message that can be shown to the end user.
 * - `logMessage`: A message that will be logged for internal tracking and debugging.
 * - `isRecoverable`: A boolean indicating if the error is recoverable (i.e., whether the user can attempt the operation again).
 */
sealed class UpdateUserProfileErrCode : AuthErrorCode {

    /**
     * Error code indicating an unsuccessful task during the profile update process.
     * This may occur due to various reasons such as failure to update user data in the backend.
     */
    data object TaskFailure : UpdateUserProfileErrCode() {
        override val name = "TASK_FAILURE"
        override val userMessage = "Falha ao completar tarefa."
        override val logMessage = "Failed on complete update user. Task returned unsuccessful."
        override val isRecoverable = true
    }

    /**
     * Error code indicating a network failure during the profile update process.
     * This happens when the operation cannot complete due to an issue with the network connection.
     */
    data object Network : UpdateUserProfileErrCode() {
        override val name = "NETWORK_ERROR"
        override val userMessage = "Erro de rede. Verifique sua conexão."
        override val logMessage = "Network failure on sign-up."
        override val isRecoverable = true
    }

    /**
     * Error code indicating an unknown error occurred during the profile update process.
     * This is a generic error that should be handled when an unforeseen issue arises.
     */
    data object Unknown : UpdateUserProfileErrCode() {
        override val name = "UNKNOWN_ERROR"
        override val userMessage = "Erro desconhecido. Tente novamente."
        override val logMessage = "Unexpected error on update users name."
        override val isRecoverable = false
    }

    /**
     * Error code indicating that no user is currently authenticated or the session has expired
     * while attempting to update users name.
     */
    data object SessionInactive : UpdateUserProfileErrCode() {
        override val name = "SESSION_INACTIVE"
        override val userMessage = "Usuário não encontrado. Tente fazer o login."
        override val logMessage = "Names update attempt failed. User not found or session expired "
        override val isRecoverable = false
    }

}