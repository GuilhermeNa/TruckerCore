package com.example.truckercore.shared.errors.abstractions

import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity

/**
 * Custom exception class used for mapping errors.
 *
 * This exception is thrown when an error occurs during the mapping process between entities and DTOs,
 * such as invalid data or a failure in the conversion process.
 * It extends the [Exception] class and allows the inclusion of both a custom message and a cause for the exception.
 *
 * @param obj The [Entity] or [Dto] related to the error.
 * @param cause The exception that caused this mapping error. This is typically another exception that was thrown during the mapping process.
 */
abstract class MappingException() : Exception()