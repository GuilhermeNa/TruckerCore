package com.example.truckercore.view.ui_error

import java.security.InvalidParameterException

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

sealed class UiResult {

    data object Success : UiResult()

    data class Error(val e: UiError) : UiResult()

}

fun <R> UiResult.mapResult(
    onSuccess: () -> R,
    onCriticalError: (UiError.Critical) -> R,
    onRecoverableError: (UiError.Recoverable) -> R
): R = when {
    this is UiResult.Success -> onSuccess()
    this is UiResult.Error && this.e is UiError.Recoverable -> onRecoverableError(this.e)
    this is UiResult.Error && this.e is UiError.Critical -> onCriticalError(this.e)
    else -> throw InvalidParameterException()
}