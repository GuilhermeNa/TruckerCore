package com.example.truckercore.infrastructure.security.authentication.errors

/**
 * Custom exception to handle cases where a Firebase user is expected, but null is encountered.
 *
 * @param message A detailed message describing the exception.
 */
class NullFirebaseUserException(message: String): Exception(message)