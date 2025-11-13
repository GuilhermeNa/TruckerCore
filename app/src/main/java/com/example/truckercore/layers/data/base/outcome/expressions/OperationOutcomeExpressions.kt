package com.example.truckercore.layers.data.base.outcome.expressions

import com.example.truckercore.core.error.core.AppException
import com.example.truckercore.layers.data.base.outcome.OperationOutcome
import com.example.truckercore.layers.presentation.viewmodels.base._contracts.Event
import com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.event.EmailAuthenticationFragmentEvent

/**
 * Maps the [OperationOutcome] to a value by applying the appropriate callback
 * based on whether the outcome is completed or a failure.
 *
 * @param onComplete Function to invoke if the outcome is [OperationOutcome.Completed].
 * @param onFailure Function to invoke if the outcome is [OperationOutcome.Failure].
 * @return The result of the corresponding function.
 */
inline fun <R> OperationOutcome.map(
    onComplete: () -> R,
    onFailure: (e: AppException) -> R
): R = when (this) {
    OperationOutcome.Completed -> onComplete()
    is OperationOutcome.Failure -> onFailure(exception)
}

/**
 * Executes the appropriate action based on whether the [OperationOutcome] is completed or a failure.
 *
 * @param onComplete Action to execute if outcome is [OperationOutcome.Completed].
 * @param onFailure Action to execute if outcome is [OperationOutcome.Failure].
 * @return The original [OperationOutcome], allowing method chaining.
 */
inline fun OperationOutcome.handle(
    onComplete: () -> Unit,
    onFailure: (AppException) -> Unit
) {
    when (this) {
        OperationOutcome.Completed -> onComplete()
        is OperationOutcome.Failure -> onFailure(exception)
    }
}
