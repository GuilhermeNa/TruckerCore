package com.example.truckercore.layers.data.base.outcome

import com.example.truckercore.core.error.core.AppException
import com.example.truckercore.layers.data.base.outcome.DataOutcome.Empty
import com.example.truckercore.layers.data.base.outcome.DataOutcome.Failure
import com.example.truckercore.layers.data.base.outcome.DataOutcome.Success

/**
 * Represents the outcome of an operation that is expected to return data.
 *
 * Useful for use cases, repositories, or network calls where data is expected but not guaranteed to be available.
 * Helps avoid using `null` or unchecked exceptions, encouraging explicit and predictable control flow.
 *
 * @see Success
 * @see Empty
 * @see Failure
 */
sealed class DataOutcome<out T> : Outcome<T> {

    /**
     * Represents a successful outcome that contains a value.
     *
     * @param data The non-null result of the operation.
     */
    data class Success<T>(val data: T) : DataOutcome<T>()

    /**
     * Represents the absence of a result (e.g., data not found).
     */
    data object Empty : DataOutcome<Nothing>()

    /**
     * Represents a failed operation due to an exception.
     *
     * @param exception The domain-specific [AppException] indicating the cause of failure.
     */
    data class Failure(val exception: AppException) : DataOutcome<Nothing>()

}

