package com.example.truckercore.core.my_lib.expressions

import com.example.truckercore.core.error.InfraException
import com.example.truckercore.core.error.core.AppException
import com.example.truckercore.layers.data.base.outcome.DataOutcome

/**
 * Maps the [DataOutcome] to a value by applying the corresponding function
 * depending on whether the outcome is a success, empty, or failure.
 *
 * @param onSuccess Function to invoke if the outcome is [DataOutcome.Success].
 * @param onEmpty Function to invoke if the outcome is [DataOutcome.Empty].
 * @param onFailure Function to invoke if the outcome is [DataOutcome.Failure].
 * @return The result of the function corresponding to the current outcome.
 */
inline fun <R, T> DataOutcome<T>.map(
    onSuccess: (data: T) -> R,
    onEmpty: () -> R,
    onFailure: (e: AppException) -> R
): R = when (this) {
    is DataOutcome.Empty -> onEmpty()
    is DataOutcome.Success -> onSuccess(data)
    is DataOutcome.Failure -> onFailure(exception)
}

/**
 * Returns the data if the [DataOutcome] is [DataOutcome.Success], or throws an [InvalidOutcomeException] otherwise.
 */
fun <T> DataOutcome<T>.get(): T = when (this) {
    is DataOutcome.Success -> data
    is DataOutcome.Empty -> throw InfraException.Outcome(
        "DataOutcome is Empty. Expected a value, but got Empty."
    )

    is DataOutcome.Failure -> throw InfraException.Outcome(
        "DataOutcome is Failure. Expected a value, but an error occurred",
        exception
    )
}

/**
 * Returns the data if the [DataOutcome] is [DataOutcome.Success], or `null` otherwise.
 */
fun <T> DataOutcome<T>.getOrNull(): T? = when (this) {
    is DataOutcome.Success -> data
    else -> null
}

/**
 * Executes the appropriate action based on the current [DataOutcome].
 *
 * @param onSuccess Action to execute if outcome is [DataOutcome.Success].
 * @param onEmpty Action to execute if outcome is [DataOutcome.Empty].
 * @param onFailure Action to execute if outcome is [DataOutcome.Failure].
 */
inline fun <T> DataOutcome<T>.handle(
    onSuccess: (T) -> Unit,
    onEmpty: () -> Unit,
    onFailure: (AppException) -> Unit
) {
    when (this) {
        is DataOutcome.Success -> onSuccess(data)
        is DataOutcome.Empty -> onEmpty()
        is DataOutcome.Failure -> onFailure(exception)
    }
}

fun  DataOutcome<*>.isNotSuccess(): Boolean = this !is DataOutcome.Success

fun DataOutcome<*>.isFailure(): Boolean = this is DataOutcome.Failure

fun DataOutcome<*>.isSuccess(): Boolean = this is DataOutcome.Success

fun DataOutcome<Boolean>.isTrue(): Boolean = this is DataOutcome.Success && this.data

fun DataOutcome<*>.isEmpty(): Boolean = this is DataOutcome.Empty

fun <T> DataOutcome<T>.onFailure(action: (AppException) -> Unit): DataOutcome<T> {
    if (this is DataOutcome.Failure) action(this.exception)
    return this
}

fun <T> DataOutcome<T>.onEmpty(action: () -> Unit): DataOutcome<T> {
    if (this is DataOutcome.Empty) action()
    return this
}

fun <T> DataOutcome<T>.onRequired(action: (T) -> Unit): DataOutcome<T> {
    if (this is DataOutcome.Success) action(this.data)
    return this
}

fun <T> DataOutcome<T>.onSuccess(action: (T) -> Unit): DataOutcome<T> {
    if (this is DataOutcome.Success) action(this.data)
    return this
}

fun  DataOutcome<*>.isConnectionError(): Boolean =
    when {
        this.isFailure() -> (this as DataOutcome.Failure).exception is InfraException.Network
        else -> false
    }





