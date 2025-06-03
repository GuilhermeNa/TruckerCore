package com.example.truckercore.view_model._shared.helpers

sealed class ViewError {

    data class Recoverable(val message: String) : ViewError()

    data class Critical(
        val title: String = DEFAULT_CRITICAL_TITLE,
        val message: String = DEFAULT_CRITICAL_MESSAGE
    ) : ViewError()

    private companion object {
        private const val DEFAULT_CRITICAL_TITLE = "Ocorreu um erro"
        private const val DEFAULT_CRITICAL_MESSAGE =
            "Algo deu errado. Por favor, tente novamente.\n" +
                    "Se o problema persistir, entre em contato com o suporte."
    }

}