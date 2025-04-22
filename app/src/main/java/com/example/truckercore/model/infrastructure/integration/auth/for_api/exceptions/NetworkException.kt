package com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions

/**
 * Indicates a network-related issue occurred while communicating with the authentication API.
 *
 * This includes timeouts, lost connectivity, or other connection failures.
 */
class NetworkException(message: String? = null, cause: Throwable? = null) :
    AuthSourceException(message, cause)