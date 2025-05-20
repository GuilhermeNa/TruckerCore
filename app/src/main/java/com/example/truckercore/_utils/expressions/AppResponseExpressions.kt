package com.example.truckercore._utils.expressions

import com.example.truckercore._utils.classes.AppResponse
import com.example.truckercore._utils.classes.AppResponseException
import com.example.truckercore.model.errors.AppException
import com.example.truckercore.model.errors.technical.TechnicalException

fun <T> AppResponse<T>.getOrNull(): T? {
    return when (this) {
        is AppResponse.Success -> data
        AppResponse.Empty -> null
        is AppResponse.Error -> null
    }
}

inline fun <R, T> AppResponse<T>.mapAppResponse(
    onSuccess: (data: T) -> R,
    onEmpty: () -> R,
    onError: (e: AppException) -> R
): R = when (this) {
    is AppResponse.Success -> onSuccess(data)
    is AppResponse.Error -> onError(exception)
    is AppResponse.Empty -> onEmpty()
}

inline fun <T> AppResponse<T>.getOrElse(
    onFailure: (AppResponse<Nothing>) -> Nothing
): T = when (this) {
    is AppResponse.Success -> data
    is AppResponse.Error -> onFailure(this)
    is AppResponse.Empty -> onFailure(this)
}

fun <T> AppResponse<T>.extractData() =
    if (this is AppResponse.Success) this.data
    else throw AppResponseException(
        "Cannot extract data: Response is not of type AppResponse.Success." +
                " Actual response is $this."
    )

fun <T> AppResponse<T>.extractError() =
    if (this is AppResponse.Error) this.exception
    else throw AppResponseException(
        "Cannot extract exception: Response is not of type AppResponse.Error." +
                " Actual response is $this."
    )

fun Exception.handleErrorResponse(unknownErrMsg: String) =
    when (this) {
        is AppException -> {
            AppResponse.Error(this)
        }

        else -> {
            val err = TechnicalException.Unknown(message = unknownErrMsg, this)
            AppResponse.Error(err)
        }
    }