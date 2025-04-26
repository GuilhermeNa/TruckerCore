package com.example.truckercore.model.shared.utils.expressions

import com.example.truckercore.model.modules.authentication.use_cases.implementations.EarlyExit

/**
 * Extension function for the [EarlyExit] class that executes the provided [block]
 * only if the Boolean value is `true`.
 *
 * This is useful for handling early exit conditions in code where the function
 * should return immediately if the Boolean condition is met.
 *
 * @param block The lambda to be executed if the Boolean value is `true`. The
 *              lambda should have a return type of `Nothing`, which typically
 *              indicates an early exit or an exception being thrown.
 */
inline fun EarlyExit.onEarlyExit(block: () -> Nothing) {
    if (this) block()
}