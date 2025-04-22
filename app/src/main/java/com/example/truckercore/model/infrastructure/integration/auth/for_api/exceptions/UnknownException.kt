package com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions

/**
 * Catches unknown or unexpected errors that occur during authentication operations.
 *
 * This is used as a fallback when no other specific exception type applies.
 */
class UnknownException(message: String? = null, cause: Throwable? = null) :
    AuthSourceException(message, cause)