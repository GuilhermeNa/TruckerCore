package com.example.truckercore.model.infrastructure.util

import com.example.truckercore.model.shared.errors.UnknownErrorException
import com.example.truckercore.model.shared.utils.expressions.logError
import com.example.truckercore.model.shared.utils.sealeds.AppResponse

internal class ExceptionHandler {

    fun run(throwable: Throwable): AppResponse.Error =
        when (throwable) {
            is Exception -> handleException(throwable)
            else -> handleError(throwable)
        }

    private fun handleException(exception: Exception): AppResponse.Error {
        logError(exception.message ?: "Unregistered exception message.")
        return AppResponse.Error(exception)
    }

    private fun handleError(throwable: Throwable): AppResponse.Error {
        logError(throwable.message ?: "Unregistered error message.")
        return AppResponse.Error(UnknownErrorException(throwable))
    }

}