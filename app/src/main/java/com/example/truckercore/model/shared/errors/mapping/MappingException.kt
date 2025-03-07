package com.example.truckercore.model.shared.errors.mapping

/**
 * Custom exception class used for mapping errors.
 *
 * This exception is thrown when an error occurs during the mapping process between entities and DTOs,
 * such as invalid data or a failure in the conversion process.
 * It extends the [Exception] class and allows the inclusion of both a custom message and a cause for the exception.
 *
 */
abstract class MappingException : Exception()