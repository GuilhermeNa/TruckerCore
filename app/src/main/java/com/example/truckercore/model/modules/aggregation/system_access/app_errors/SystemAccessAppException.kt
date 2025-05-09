package com.example.truckercore.model.modules.aggregation.system_access.app_errors

import com.example.truckercore.model.errors.AppException

/**
 * Exception class representing errors that occur during the system access creation process.
 *
 * This exception wraps domain-specific errors related to the initialization or configuration
 * of user access within the system, such as failures in factories, invalid data, or missing dependencies.
 *
 * @param message A user-friendly or developer-specific message describing the exception.
 * @param cause The underlying cause of the exception, if available.
 * @param errorCode A specific code representing the type of system access error.
 *
 * @see SystemAccessErrCode For the available error codes related to system access.
 */
class SystemAccessAppException(
    message: String?,
    cause: Throwable? = null,
    errorCode: SystemAccessErrCode
) : AppException(message, cause, errorCode)