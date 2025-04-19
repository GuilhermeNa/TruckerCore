package com.example.truckercore.model.infrastructure.app_exception

/**
 * Base class for all application-specific exceptions.
 *
 * This abstract class represents any exception that can occur in the application,
 * and links it with a specific [ErrorCode] that provides metadata about the error.
 *
 * @property message A human-readable message describing the error.
 * @property cause The underlying exception that caused this error (optional).
 * @property errorCode The specific [ErrorCode] that categorizes and describes the error.
 *
 * @see ErrorCode for detailed metadata about the error.
 */
abstract class AppException(
    message: String? = null,
    cause: Throwable? = null,
    val errorCode: ErrorCode
) : Exception(message, cause)