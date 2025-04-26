package com.example.truckercore.model.shared.utils.expressions

import com.example.truckercore.model.infrastructure.app_exception.AppException
import com.example.truckercore.model.shared.utils.sealeds.AppResponse

fun <R, T> AppResponse<T>.mapAppResponse(
    success: (data: T) -> R,
    empty: () -> R,
    error: (e: AppException) -> R
): R = when (this) {
    is AppResponse.Success -> success(data)
    is AppResponse.Error -> error(exception)
    is AppResponse.Empty -> empty()
}

fun <T> AppResponse<T>.extractData() =
    if(this is AppResponse.Success) this.data
    else throw ClassCastException(
        "Cannot extract data: Response is not of type AppResponse.Success." +
                " Actual response is $this."
    )

fun <T> AppResponse<T>.extractError() =
    if(this is AppResponse.Error) this.exception
    else throw ClassCastException(
        "Cannot extract exception: Response is not of type AppResponse.Error." +
                " Actual response is $this."
    )