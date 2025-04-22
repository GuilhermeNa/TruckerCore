package com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions

/**
 * Represents a general failure during an authentication-related task.
 *
 * Used when an operation fails for unspecified reasons not covered by other exceptions.
 */
class TaskFailureException(message: String? = null, cause: Throwable? = null) :
    AuthSourceException(message, cause)