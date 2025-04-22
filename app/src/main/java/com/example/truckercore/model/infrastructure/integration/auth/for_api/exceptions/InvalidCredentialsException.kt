package com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions

/**
 * Thrown when the authentication credentials (such as email or password) are invalid.
 *
 * This can occur during login attempts, registration, or other credential verification processes.
 */
class InvalidCredentialsException(message: String? = null, cause: Throwable? = null) :
    AuthSourceException(message, cause)