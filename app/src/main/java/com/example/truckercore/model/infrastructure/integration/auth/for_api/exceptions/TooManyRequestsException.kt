package com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions

/**
 * Thrown when too many requests are made to the authentication API in a short period.
 *
 * Commonly returned in rate-limiting scenarios such as excessive login attempts.
 */
class TooManyRequestsException(message: String? = null, cause: Throwable? = null) :
    AuthSourceException(message, cause)