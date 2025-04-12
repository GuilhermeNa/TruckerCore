package com.example.truckercore.model.shared.utils.sealeds

import com.example.truckercore.model.shared.utils.sealeds.Result.Error
import com.example.truckercore.model.shared.utils.sealeds.Result.Success

sealed class Result<out T> {

    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    fun extractData() = (this as? Success)?.data

    fun extractException() = (this as? Error)?.exception

    fun ifError(block: (error: Exception) -> Unit) {
        if (this is Error) {
            block(this.exception)
        }
    }

}

fun <T>Result<T>.handleResult(
    success: (data: T) -> Unit,
    error: (e: Exception) -> Unit
) {
    when (this) {
        is Success -> success(data)
        is Error -> error(exception)
    }
}

fun <T, R> Result<T>.mapResult(
    success: (data: T) -> R,
    error: (e: Exception) -> R
): R = when (this) {
    is Success -> success(data)
    is Error -> error(exception)
}