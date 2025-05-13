package com.example.truckercore.view.ui_error

import com.example.truckercore.model.errors.exceptions.AppException
import com.example.truckercore.model.errors.exceptions.InfraException

object UiErrorFactory {

    private const val UNRECOVERABLE_ERR_TITLE = "Sistema falhou"
    private const val UNRECOVERABLE_ERR_MESSAGE =
        "Ocorreu um erro inesperado. Tente novamente mais tarde ou entre em contato com o suporte."

    operator fun invoke(e: AppException): UiError = when (e) {
        is InfraException.NetworkUnavailable -> UiError.Recoverable.Network
        else -> {
            UiError.Critical(
                title = UNRECOVERABLE_ERR_TITLE,
                message = UNRECOVERABLE_ERR_MESSAGE
            )
        }
    }


}