package com.example.truckercore.core.error.core

/**
 * Base exception for the entire application.
 *
 * All custom exceptions in the app must extend this class.
 * It provides a common root for error handling across
 * data, domain, infrastructure, and presentation layers.
 *
 * @param message Optional detail message describing the error.
 * @param cause Optional underlying cause of the exception.
 */
open class AppException(
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause)