package com.example.truckercore._shared.expressions

import com.example.truckercore._shared.classes.AppResult
import com.example.truckercore._shared.classes.AppResult.Error
import com.example.truckercore._shared.classes.AppResult.Success
import com.example.truckercore.model.errors.AppException

inline fun <T, R> AppResult<T>.mapAppResult(
    onSuccess: (data: T) -> R,
    onError: (e: AppException) -> R
): R = when (this) {
    is Success -> onSuccess(data)
    is Error -> onError(exception)
}

inline fun <T> AppResult<T>.getOrElse(
    orElse: (Error) -> Nothing
): T = when (this) {
    is Success -> this.data
    is Error -> orElse(this)
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

suspend inline fun <T> AppResult<T>.runSuspendOnSuccess(crossinline block: suspend () -> Unit) {
    if (this is Success) block()
}