package com.example.truckercore.view_model._shared.expressions

import com.example.truckercore.view_model._shared.helpers.ViewError
import com.example.truckercore.view_model._shared.helpers.ViewResult
import java.security.InvalidParameterException

fun <R> ViewResult.mapResult(
    onSuccess: () -> R,
    onCriticalError: (ViewError.Critical) -> R,
    onRecoverableError: (ViewError.Recoverable) -> R
): R = when {
    this is ViewResult.Success -> onSuccess()
    this is ViewResult.Error && this.e is ViewError.Recoverable -> onRecoverableError(this.e)
    this is ViewResult.Error && this.e is ViewError.Critical -> onCriticalError(this.e)
    else -> throw InvalidParameterException()
}