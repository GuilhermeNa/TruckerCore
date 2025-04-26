package com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions

/**
 * Exception thrown when a network error occurs while retrieving data from the data source.
 *
 * This may happen due to connectivity issues, timeouts, or other network-related failures.
 *
 * @param message A descriptive message explaining the network error.
 * @param cause The underlying cause of the exception, if available.
 */
class NetworkException(message: String? = null, cause: Throwable? = null) :
    DataSourceException(message, cause)