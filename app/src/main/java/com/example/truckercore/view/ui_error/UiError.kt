package com.example.truckercore.view.ui_error

sealed class UiError {

    sealed class Recoverable(open val message: String) : UiError() {
        data object Network : Recoverable("Falha de conexão.")
        data object Unauthorized : Recoverable("Usuário não autorizado.")
        data class Custom(override val message: String) : Recoverable(message)
    }

    data class Critical(val title: String, val message: String) : UiError()

}
