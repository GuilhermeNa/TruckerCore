package com.example.truckercore.shared.exceptions

/**
 * Custom exception class that is thrown when a required field is missing.
 * This exception is used to signal that a necessary field was not provided or is null
 * when it was expected to be non-null in a data transformation or validation process.
 *
 * @param message A detailed message explaining the cause of the exception.
 *                It can be used to specify which field is missing or why the exception was thrown.
 */
class MissingFieldException(message: String? = null): Exception(message)