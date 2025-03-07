package com.example.truckercore.model.infrastructure.security.authentication.errors

/**
 * Exception thrown when an invalid password is encountered.
 *
 * This exception is typically used when a password does not meet the required criteria or validation rules.
 * It allows developers to provide a custom error message explaining why the password is invalid.
 *
 * @param message The detail message explaining why the password is invalid.
 */
class InvalidPasswordException(message: String) : Exception(message)