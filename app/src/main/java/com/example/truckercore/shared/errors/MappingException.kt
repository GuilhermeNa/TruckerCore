package com.example.truckercore.shared.errors

/**
 * Custom exception class used for mapping errors.
 *
 * This exception is thrown when an error occurs during the mapping process between entities and DTOs,
 * such as invalid data or a failure in the conversion process.
 * It extends the [Exception] class and allows the inclusion of both a custom message and a cause for the exception.
 *
 * @param message An optional message that provides details about the mapping error. If no message is provided, the default value is `null`.
 * @param cause The exception that caused this mapping error. This is typically another exception that was thrown during the mapping process.
 */
abstract class MappingException(message: String? = null, cause: Exception) :
    Exception(message, cause)