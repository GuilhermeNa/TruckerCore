package com.example.truckercore.view.ui_error

sealed class UiError {

    sealed class Recoverable(open val message: String) : UiError() {
        data object Network : Recoverable("Falha de conexão.")
        data object SessionInactive: Recoverable("Usuário desconectado. Faça o Login novamente.")
        data object Unauthorized : Recoverable("Usuário não autorizado.")
        data class Custom(override val message: String) : Recoverable(message)
    }

    data class Critical(
        val title: String = "Ocorreu um erro",
        val message: String = "Algo deu errado. Por favor, tente novamente.\n" +
                "Se o problema persistir, entre em contato com o suporte."
    ) : UiError()

}
