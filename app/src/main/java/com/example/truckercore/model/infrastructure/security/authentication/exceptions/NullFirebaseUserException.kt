package com.example.truckercore.model.infrastructure.security.authentication.exceptions

/**
 * Exception thrown when a Firebase operation requires a logged-in user,
 * but the current Firebase user is null.
 */
class NullFirebaseUserException(message: String? = null, cause: Throwable? = null) :
    Exception(message, cause)