package com.example.truckercore.model.shared.utils.expressions

import com.example.truckercore.model.infrastructure.integration.exceptions.AppException
import com.example.truckercore.model.shared.utils.sealeds.AppResult
import com.example.truckercore.model.shared.utils.sealeds.AppResult.Error
import com.example.truckercore.model.shared.utils.sealeds.AppResult.Success

fun <T, R> AppResult<T>.mapAppResult(
    success: (data: T) -> R,
    error: (e: AppException) -> R
): R = when (this) {
    is Success -> success(data)
    is Error -> error(exception)
}

fun <T> AppResult<T>.handleAppResult(
    success: (data: T) -> Unit,
    error: (e: AppException) -> Unit
) = when (this) {
    is Success -> success(data)
    is Error -> error(exception)
}
