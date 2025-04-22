package com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions

/**
 * Base class for all exceptions related to data source operations.
 *
 * All errors that occur during data retrieval, interpretation, or transformation in the data layer
 * should extend this exception. This allows the application to treat data-related issues in a consistent
 * and predictable way, regardless of the backend.
 *
 * @param message A descriptive message explaining the error.
 * @param cause The underlying cause of the exception, if available.
 *
 */
abstract class DataSourceException(message: String? = null, cause: Throwable? = null):
        Exception(message, cause)