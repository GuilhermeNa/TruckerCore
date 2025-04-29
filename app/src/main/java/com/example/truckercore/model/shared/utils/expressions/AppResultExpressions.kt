package com.example.truckercore.model.shared.utils.expressions

import com.example.truckercore.model.infrastructure.app_exception.AppException
import com.example.truckercore.model.shared.utils.sealeds.AppResult
import com.example.truckercore.model.shared.utils.sealeds.AppResult.Error
import com.example.truckercore.model.shared.utils.sealeds.AppResult.Success

fun <T, R> AppResult<T>.mapAppResult(
    onSuccess: (data: T) -> R,
    onError: (e: AppException) -> R
): R = when (this) {
    is Success -> onSuccess(data)
    is Error -> onError(exception)
}

fun <T> AppResult<T>.handleAppResult(
    onSuccess: (data: T) -> Unit,
    onError: (e: AppException) -> Unit
) = when (this) {
    is Success -> onSuccess(data)
    is Error -> onError(exception)
}

fun <T> AppResult<T>.extractData() =
    if (this is Success) this.data
    else throw ClassCastException(
        "Cannot extract data: Result is not of type AppResult.Success." +
                " Actual response is $this."
    )

fun <T> AppResult<T>.extractError() =
    if (this is Error) this.exception
    else throw ClassCastException(
        "Cannot extract exception: Result is not of type AppResult.Error." +
                " Actual response is $this."
    )