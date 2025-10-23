package com.example.truckercore.layers.data.base.outcome

import com.example.truckercore.core.error.core.AppException

/**
 * Represents the outcome of an operation that does not return data,
 * indicating either success or failure.
 * ### Example:
 * ```
 * val outcome = createAccount()
 * when (outcome) {
 *     is OperationOutcome.Completed -> { /* Account created successfully */ }
 *     is OperationOutcome.Failure -> { e -> /* Handle error */ }
 * }
 * ```
 */
sealed class OperationOutcome : Outcome<Unit> {

    /**
     * Represents a successful operation, regardless of whether it caused a change.
     */
    data object Completed : OperationOutcome()

    /**
     * Represents a failed operation due to a domain or infrastructure exception.
     *
     * @param exception The [AppException] that caused the failure.
     */
    data class Failure(val exception: AppException) : OperationOutcome()

}
