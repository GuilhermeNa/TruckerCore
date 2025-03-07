package com.example.truckercore.shared.errors

/**
 * Custom exception thrown when an invalid enum parameter is encountered.
 *
 * This exception is used to indicate when an invalid string is provided for an enum conversion.
 *
 * @param message An optional message that describes the cause of the exception.
 */
class InvalidEnumParameterException(message: String) : Exception(message)