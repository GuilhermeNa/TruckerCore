package com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions

/**
 * Thrown when an operation is attempted with an inactive or expired user session.
 *
 * Usually triggered when the user is not authenticated or their session has expired.
 */
class SessionInactiveException(message: String? = null, cause: Throwable? = null) :
    AuthSourceException(message, cause)