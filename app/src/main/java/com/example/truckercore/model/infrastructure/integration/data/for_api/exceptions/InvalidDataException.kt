package com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions

/**
 * Exception thrown when the data from the data source is invalid.
 *
 * This may happen when the data retrieved from the source does not meet the expected format or
 * is incorrect, causing it to be unusable.
 *
 * @param message A descriptive message explaining the invalid data.
 * @param cause The underlying cause of the exception, if available.
 */
class InvalidDataException(message: String? = null, cause: Throwable? = null) :
    DataSourceException(message, cause)