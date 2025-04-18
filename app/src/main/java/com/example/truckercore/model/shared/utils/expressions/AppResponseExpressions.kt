package com.example.truckercore.model.shared.utils.expressions

import com.example.truckercore.model.infrastructure.integration.exceptions.AppException
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

