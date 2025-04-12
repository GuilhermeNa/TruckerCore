package com.example.truckercore.view.sealeds

import com.example.truckercore.view.activities.NotificationActivity

private const val NO_CONNECTION_ERROR_MSG = "Falha de conexão com a internet."

private const val UNKNOWN_ERROR_MSG = "Falha de conexão com a internet."

private const val SESSION_EXPIRED_ERROR_MSG = "Sessão expirada ou usuário não autenticado."

private const val SERVER_ERROR_MSG = "Nosso servidor está com alguma instabilidade no momento. Tente novamente em breve."


typealias UiFatalError = UiError.FatalError
typealias UiCustomError = UiError.RecoverableError.Custom
typealias UiNoConnectionError =  UiError.RecoverableError.NoConnection
typealias UiUnknownError =  UiError.RecoverableError.Unknown
typealias UiSessionExpiredError =  UiError.RecoverableError.SessionExpired
typealias UiServerError =  UiError.RecoverableError.ServerError


/**
 * Represents errors that are meant to be handled at the UI layer.
 *
 * This sealed class separates unrecoverable (fatal) errors from recoverable ones.
 */
sealed class UiError {

    /**
     * A critical and unrecoverable error that prevents the app from continuing.
     *
     * When this error is triggered, the user should be redirected to a dedicated
     * error screen (e.g., [NotificationActivity]) informing them about the issue and
     * suggesting they restart or close the application.
     *
     * @property title A short title describing the fatal error.
     * @property message A more detailed message explaining the error.
     */
    data class FatalError(val title: String, val message: String) : UiError()

    /**
     * Represents recoverable errors that can be gracefully handled within the app UI.
     *
     * These errors are typically shown to the user using toasts, snackbars, or dialogs.
     * They do not require terminating the app or navigating away from the current screen.
     */
    sealed class RecoverableError(open val message: String) : UiError() {

        /**
         * A custom recoverable error with a user-defined message.
         *
         * @property message A human-readable message to display to the user.
         */
        data class Custom(override val message: String) : RecoverableError(message)

        /**
         * Indicates a failure in network connectivity.
         *
         * @property message A predefined message for no connection scenarios.
         */
        data class NoConnection(override val message: String = NO_CONNECTION_ERROR_MSG) : RecoverableError(message)

        /**
         * Represents an unknown or unexpected error that couldn't be categorized.
         *
         * @property message A generic fallback message.
         */
        data class Unknown(override val message: String = UNKNOWN_ERROR_MSG) : RecoverableError(message)

        /**
         * Indicates that the user's session has expired or they are unauthenticated.
         *
         * This often leads to a logout or navigation to the login screen.
         *
         * @property message Message informing the user that reauthentication is required.
         */
        data class SessionExpired(override val message: String = SESSION_EXPIRED_ERROR_MSG) : RecoverableError(message)

        data class ServerError(override val message: String = SERVER_ERROR_MSG) : RecoverableError(message)

    }

}