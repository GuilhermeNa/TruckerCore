package com.example.truckercore.shared.utils.sealeds

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
    data class Success<T>(val data: T): Response<T>()

    /**
     * Represents an error response encapsulating an [Exception].
     *
     * @property exception The exception object that caused the error.
     */
    data class Error(val exception: Exception): Response<Nothing>()

    /**
     * Represents a response indicating that no data was found or the operation resulted in an empty state.
     */
    data object Empty: Response<Nothing>()


    fun isSuccess() = this is Success

    fun isEmpty() = this is Empty

}