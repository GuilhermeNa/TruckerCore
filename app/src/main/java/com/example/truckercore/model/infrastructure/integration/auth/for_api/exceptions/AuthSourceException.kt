package com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions

/**
 * Base exception for all authentication-related errors that originate from a remote data source.
 *
 * This exception is used to abstract the underlying API implementation details from the application layer.
 */
abstract class AuthSourceException(message: String? = null, cause: Throwable? = null) :
    Exception(message, cause)