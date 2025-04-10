package com.example.truckercore.model.shared.utils.sealeds


/**
 * Sealed class representing a response from an asynchronous operation.
 * Allows encapsulating either a successful result with data of type [T],
 * or an error with an [Exception].
 */
sealed class Response<out T> {

    /**
     * Represents a successful response with optional data of type [T].
     *
     * @property data Optional data of type [T] representing the result of a successful operation.
     */
    data class Success<T>(val data: T) : Response<T>()

    /**
     * Represents an error response encapsulating an [Exception].
     *
     * @property exception The exception object that caused the error.
     */
    data class Error(val exception: Exception) : Response<Nothing>()

    /**
     * Represents a response indicating that no data was found or the operation resulted in an empty state.
     */
    data object Empty : Response<Nothing>()

    fun extractData() = (this as? Success)?.data

    fun extractException() = (this as? Error)?.exception

    fun isEmpty() = this is Empty

    fun <C>handleResponseAndConsume(
        success: (data: T) -> C,
        empty: () -> C,
        error: (e: Exception) -> C
    ): C = when (this) {
        is Success -> success(data)
        is Empty -> empty()
        is Error -> error(exception)
    }

    fun handleResponse(
        success: (data: T) -> Unit,
        empty: () -> Unit,
        error: (e: Exception) -> Unit
    ) = when (this) {
        is Success -> success(data)
        is Empty -> empty()
        is Error -> error(exception)
    }


}