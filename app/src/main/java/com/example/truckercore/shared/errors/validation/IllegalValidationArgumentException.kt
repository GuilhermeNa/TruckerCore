package com.example.truckercore.shared.errors.validation

import com.example.truckercore.shared.errors.abstractions.ValidationException
import kotlin.reflect.KClass

/**
 * A custom exception that is thrown when an unexpected input is encountered during the validation process.
 *
 * This exception extends from [ValidationException] and is used to indicate that the input provided
 * to a validator does not match the expected type or structure.
 * It is typically thrown when there is a mismatch between the expected input class and the actual class received.
 *
 * @param message The detail message for the exception, explaining the cause of the error. This message is optional and can be null.
 */
class IllegalValidationArgumentException(
    val expected: KClass<*>,
    val received: KClass<*>
) : ValidationException() {

    fun message(): String = "Expected a ${expected.simpleName} " +
            "for validation but received: ${received.simpleName}."

}