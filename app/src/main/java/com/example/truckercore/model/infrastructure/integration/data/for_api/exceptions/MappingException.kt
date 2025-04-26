package com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions

/**
 * Exception thrown when there is an error in mapping data from one format to another.
 *
 * This could occur during transformations or when trying to map data from one domain object to another
 * that doesn't match the expected structure.
 *
 * @param message A descriptive message explaining the mapping error.
 * @param cause The underlying cause of the exception, if available.
 */
class MappingException(message: String? = null, cause: Throwable? = null) :
    DataSourceException(message, cause)
