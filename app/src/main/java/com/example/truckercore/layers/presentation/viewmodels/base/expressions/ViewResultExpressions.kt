package com.example.truckercore.layers.presentation.viewmodels.base.expressions

import com.example.truckercore.core.expressions.getClassName
import com.example.truckercore.core.util.AppLogger
import com.example.truckercore.domain._shared.helpers.ViewError
import com.example.truckercore.domain._shared.helpers.ViewResult
import com.example.truckercore.layers.presentation.viewmodels.base.helpers.ViewError
import com.example.truckercore.layers.presentation.viewmodels.base.helpers.ViewResult
import java.security.InvalidParameterException

fun <R, T> ViewResult<T>.mapResult(
    onSuccess: (T) -> R,
    onCriticalError: (ViewError.Critical) -> R,
    onRecoverableError: (ViewError.Recoverable) -> R
): R = when {
    this is ViewResult.Success -> onSuccess(data)
    this is ViewResult.Error && e is ViewError.Recoverable -> onRecoverableError(e)
    this is ViewResult.Error && e is ViewError.Critical -> onCriticalError(e)
    else -> throw InvalidParameterException()
}

fun <T> ViewResult<T>.getOrElse(orElse: (ViewError) -> Nothing): T =
    when (this) {
        is ViewResult.Success -> this.data
        is ViewResult.Error -> orElse(this.e)
    }

fun <T> ViewResult<T>.get() = (this as ViewResult.Success).data

fun <T> ViewResult<T>.handle(
    onSuccess: (T) -> Unit,
    onCriticalError: (ViewError.Critical) -> Unit,
    onRecoverableError: (ViewError.Recoverable) -> Unit
) {
    when {
        this is ViewResult.Success -> {
            AppLogger.d(getClassName(), "Success: $data")
            onSuccess(data)
        }
        this is ViewResult.Error && this.e is ViewError.Recoverable -> {
            AppLogger.e(getClassName(), "Recoverable Error: $e")
            onRecoverableError(this.e)
        }
        this is ViewResult.Error && this.e is ViewError.Critical -> {
            AppLogger.e(getClassName(), "Critical Error: $e")
            onCriticalError(this.e)
        }
        else -> throw InvalidParameterException()
    }
}