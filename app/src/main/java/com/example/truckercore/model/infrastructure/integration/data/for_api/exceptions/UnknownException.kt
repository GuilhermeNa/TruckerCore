package com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions

/**
 * Exception thrown when an unknown error occurs during data source operations.
 *
 * This acts as a fallback for unexpected or unhandled errors. It may be used when the cause is unknown
 * or when no other specific exception is appropriate.
 *
 * @param message A descriptive message explaining the unknown error.
 * @param cause The underlying cause of the exception, if available.
 */
class UnknownException(message: String? = null, cause: Throwable? = null) :
    DataSourceException(message, cause)