package com.example.truckercore.shared.errors.validation

import kotlin.reflect.KClass

/**
 * Custom exception class that represents an error when an argument passed for validation
 * does not match the expected type.
 *
 * @param expected The expected class type for validation.
 * @param received The class type that was actually received.
 */
class IllegalValidationArgumentException(val expected: KClass<*>, val received: KClass<*>) :
    ValidationException() {

    override val message: String
        get() = "Expected a ${expected.simpleName} " +
                "for validation but received: ${received.simpleName}."

}