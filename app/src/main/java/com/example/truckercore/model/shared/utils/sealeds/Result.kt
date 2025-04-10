package com.example.truckercore.model.shared.utils.sealeds

sealed class Result<out T> {

    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    fun handleResult(
        success: (data: T) -> Unit,
        error: (e: Exception) -> Unit
    ) {
        when (this) {
            is Success -> success(data)
            is Error -> error(exception)
        }
    }

    fun extractData() = (this as? Success)?.data

    fun extractException() = (this as? Error)?.exception

    fun ifError(block: (error: Exception) -> Unit) {
        if (this is Error) {
            block(this.exception)
        }
    }

}