package com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions

/**
 * Indicates that the password provided does not meet the minimum required strength or security criteria.
 *
 * Usually triggered during user registration or password update operations.
 */
class WeakPasswordException(message: String? = null, cause: Throwable? = null) :
    AuthSourceException(message, cause)