package com.example.truckercore.shared.errors

/**
 * Exception thrown when an unknown error occurs.
 *
 * This exception is used to wrap and propagate an underlying throwable or error that is not specifically
 * handled by other exceptions. It can be useful in scenarios where an unexpected or unclassified error occurs,
 * allowing for better error tracking and debugging.
 *
 * @param throwable The underlying throwable or error that caused this exception to be thrown.
 */
class UnknownErrorException(throwable: Throwable): Exception(throwable)