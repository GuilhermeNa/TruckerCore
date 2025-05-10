package com.example.truckercore.model.shared.utils.sealeds

import com.example.truckercore.model.errors.AppExceptionOld

sealed class AppResponse<out T> {

    data class Success<T>(val data: T) : AppResponse<T>()

    data class Error(val exception: AppExceptionOld) : AppResponse<Nothing>()

    data object Empty : AppResponse<Nothing>()

}

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
    onError: (e: AppExceptionOld) -> R
): R = when (this) {
    is AppResponse.Success -> onSuccess(data)
    is AppResponse.Error -> onError(exception)
    is AppResponse.Empty -> onEmpty()
}

inline fun <T> AppResponse<T>.getOrReturn(
    block: (AppResponse<Nothing>) -> Nothing
): T = when (this) {
    is AppResponse.Success -> data
    is AppResponse.Error -> block(AppResponse.Error(exception))
    is AppResponse.Empty -> block(AppResponse.Empty)
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