package com.example.truckercore.infrastructure.security.permissions.errors

/**
 * Exception thrown when an unauthorized action is attempted by a user or service.
 *
 * This exception is typically used when a user or system does not have the necessary permissions
 * to perform a specific action. It is useful in scenarios where access control and security
 * are enforced, and an unauthorized action needs to be caught and handled appropriately.
 *
 * @param message An optional message providing more details about the exception. If no message is
 *                provided, the default message from the superclass (`Exception`) will be used.
 */
class UnauthorizedAccessException(message: String? = null): Exception(message)