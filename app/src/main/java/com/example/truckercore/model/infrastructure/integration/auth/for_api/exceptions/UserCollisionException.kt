package com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions

/**
 * Thrown when an attempt to create a user conflicts with an existing user.
 *
 * Typically occurs when trying to register with an email that is already associated with an account.
 */
class UserCollisionException(message: String? = null, cause: Throwable? = null) :
    AuthSourceException(message, cause)