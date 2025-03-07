package com.example.truckercore.infrastructure.security.authentication.errors

/**
 * Exception thrown when an invalid email is encountered.
 *
 * This exception is typically used when an email does not meet the expected format or validation criteria.
 * It allows developers to provide a custom error message to explain why the email is invalid.
 *
 * @param message The detail message explaining why the email is invalid.
 */
class InvalidEmailException(message: String): Exception(message)