package com.example.truckercore.model.shared.utils.sealeds

import com.example.truckercore.model.errors.AppException

sealed class AppResponse<out T> {

    data class Success<T>(val data: T) : AppResponse<T>()

    data class Error(val exception: AppException) : AppResponse<Nothing>()

    data object Empty : AppResponse<Nothing>()

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

inline fun <T, R> AppResponse<T>.getOrElse(
    onSuccess: (data: T) -> R,
    orElse: (AppResponse<Nothing>) -> Nothing
): R = when (this) {
    is AppResponse.Success -> onSuccess(data)
    is AppResponse.Error -> orElse(AppResponse.Error(exception))
    is AppResponse.Empty -> orElse(AppResponse.Empty)
}

fun <T> AppResponse<T>.extractData() =
    if (this is AppResponse.Success) this.data
    else throw ClassCastException(
        "Cannot extract data: Response is not of type AppResponse.Success." +
                " Actual response is $this."
    )

fun <T> AppResponse<T>.extractError() =
    if (this is AppResponse.Error) this.exception
    else throw ClassCastException(
        "Cannot extract exception: Response is not of type AppResponse.Error." +
                " Actual response is $this."
    )