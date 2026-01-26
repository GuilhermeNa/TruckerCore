package com.example.truckercore.core.error

import com.example.truckercore.core.error.core.AppException

/**
 * Represents errors related to the data layer.
 *
 * These exceptions usually occur while interacting with
 * data sources such as databases, APIs, or authentication providers.
 */
sealed class DataException(
    message: String? = null,
    cause: Throwable? = null
) : AppException(message, cause) {

    /**
     * Represents an unknown data-related error.
     */
    class Unknown(
        message: String = "An unknown error occurred.",
        cause: Throwable? = null
    ) : DataException(message, cause)

    /**
     * Represents an error originating from a data source.
     */
    class DataSource(
        message: String = "An unknown error occurred on data source.",
        cause: Throwable? = null
    ) : DataException(message, cause)

    /**
     * Represents an error related to authentication data sources.
     */
    class AuthSource(
        message: String = "An unknown error occurred on authentication source.",
        cause: Throwable? = null
    ) : DataException(message, cause)

    /**
     * Represents a rate-limiting error when performing requests.
     */
    class TooManyRequests(
        message: String = "Too many requests.",
        cause: Throwable? = null
    ) : DataException(message, cause)

    /**
     * Represents an error while mapping or transforming data models.
     */
    class Mapping(
        message: String = "An error occurred while mapping an object.",
        cause: Throwable? = null
    ) : InfraException(message, cause)
}