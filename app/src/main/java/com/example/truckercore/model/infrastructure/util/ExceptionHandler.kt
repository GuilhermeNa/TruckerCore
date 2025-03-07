package com.example.truckercore.model.infrastructure.util

import com.example.truckercore.model.shared.errors.UnknownErrorException
import com.example.truckercore.model.shared.utils.expressions.logError
import com.example.truckercore.model.shared.utils.sealeds.Response

internal class ExceptionHandler {

    fun run(throwable: Throwable): Response.Error =
        when (throwable) {
            is Exception -> handleException(throwable)
            else -> handleError(throwable)
        }

    private fun handleException(exception: Exception): Response.Error {
        logError(exception.message ?: "Unregistered exception message.")
        return Response.Error(exception)
    }

    private fun handleError(throwable: Throwable): Response.Error {
        logError(throwable.message ?: "Unregistered error message .")
        return Response.Error(UnknownErrorException(throwable))
    }

}