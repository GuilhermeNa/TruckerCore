package com.example.truckercore.shared.errors.strategy

import com.example.truckercore.shared.resolvers.ValidatorStrategyResolver

/**
 * Exception thrown when no validation strategy is found for a given class type.
 *
 * This exception is raised by the [ValidatorStrategyResolver] when it cannot find a matching strategy
 * for the provided class type.
 *
 * @param message The message describing the error.
 */
class StrategyNotFoundException(message: String) : Exception(message)
