package com.example.truckercore.view.ui_error

sealed class UiError {

    data class Recoverable(val message: String) : UiError()

    data class Critical(
        val title: String = DEFAULT_CRITICAL_TITLE,
        val message: String = DEFAULT_CRITICAL_MESSAGE
    ) : UiError()

    private companion object {
        private const val DEFAULT_CRITICAL_TITLE = "Ocorreu um erro"
        private const val DEFAULT_CRITICAL_MESSAGE =
            "Algo deu errado. Por favor, tente novamente.\n" +
                    "Se o problema persistir, entre em contato com o suporte."
    }

}
