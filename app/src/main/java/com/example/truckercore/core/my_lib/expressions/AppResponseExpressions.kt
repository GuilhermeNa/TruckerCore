package com.example.truckercore.core.my_lib.expressions

import com.example.truckercore.data.shared.outcome.data.DataOutcome
import com.example.truckercore.data.shared.outcome.InvalidOutcomeException

fun <T> DataOutcome<T>.getOrNull(): T? {
    return when (this) {
        is DataOutcome.Success -> data
        DataOutcome.Empty -> null
        is DataOutcome.Failure -> null
    }
}

fun <T> DataOutcome<T>.get() = (this as DataOutcome.Success).data

inline fun <R, T> DataOutcome<T>.mapAppResponse(
    onSuccess: (data: T) -> R,
    onEmpty: () -> R,
    onError: (e: com.example.truckercore.data.infrastructure.app_errors.abstractions.AppException) -> R
): R = when (this) {
    is DataOutcome.Success -> onSuccess(data)
    is DataOutcome.Failure -> onError(exception)
    is DataOutcome.Empty -> onEmpty()
}

inline fun <T> DataOutcome<T>.getOrElse(
    onFailure: (DataOutcome<Nothing>) -> Nothing
): T = when (this) {
    is DataOutcome.Success -> data
    is DataOutcome.Failure -> onFailure(this)
    is DataOutcome.Empty -> onFailure(this)
}

fun <T> DataOutcome<T>.extractData() =
    if (this is DataOutcome.Success) this.data
    else throw InvalidOutcomeException(
        "Cannot extract data: Response is not of type AppResponse.Success." +
                " Actual response is $this."
    )

fun <T> DataOutcome<T>.extractError() =
    if (this is DataOutcome.Failure) this.exception
    else throw InvalidOutcomeException(
        "Cannot extract exception: Response is not of type AppResponse.Error." +
                " Actual response is $this."
    )

fun Exception.handleErrorResponse(unknownErrMsg: String) =
    when (this) {
        is com.example.truckercore.data.infrastructure.app_errors.abstractions.AppException -> {
            DataOutcome.Failure(this)
        }

        else -> {
            val err = com.example.truckercore.core.error.classes.technical.TechnicalException.Unknown(message = unknownErrMsg, this)
            DataOutcome.Failure(err)
        }
    }