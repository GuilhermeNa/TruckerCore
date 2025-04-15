package com.example.truckercore.model.infrastructure.security.authentication.app_errors

import com.example.truckercore.model.shared.errors._main.AppException

/**
 * Custom exception class used to represent authentication module related errors.
 *
 * This class extends [AppException] and is designed to handle exceptions that occur during authentication operations.
 *
 * @param message The detailed message explaining the exception. This message will typically be user-friendly and
 *                will be extracted from the associated [AuthErrorCode].
 * @param cause The optional [Throwable] cause for this exception. This is typically the original exception that
 *              caused the authentication error (e.g., Firebase exception).
 * @param errorCode The specific [AuthErrorCode] associated with this authentication error. This provides context for
 *                  logging and further handling of the error.
 */
class AuthenticationAppException(
    message: String?,
    cause: Throwable? = null,
    errorCode: AuthErrorCode
) : AppException(message, cause, errorCode)



