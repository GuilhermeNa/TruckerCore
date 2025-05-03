package com.example.truckercore.model.infrastructure.integration.data.for_app.app_errors

import com.example.truckercore.model.errors.AppException
import com.example.truckercore.model.errors.ErrorCode

/**
 * Exception class used to represent domain-specific errors in the data layer.
 *
 * This exception wraps:
 * - A user-friendly message (optional)
 * - A technical cause ([Throwable]), for stack traces or debugging (optional)
 * - A domain-specific [ErrorCode] indicating the nature of the failure
 *
 * This class enables consistent error handling and logging for all data-related
 * operations, whether it's fetching, transforming, or streaming data.
 *
 * @param message Optional message that can be displayed or logged.
 * @param cause Optional cause for the exception, useful for debugging.
 * @param errorCode A specific [ErrorCode] that categorizes the failure (e.g., [DataFindErrCode], [DataFlowErrCode]).
 */
class DataAppException(
    message: String? = null,
    cause: Throwable? = null,
    errorCode: DataErrorCode
): AppException(message, cause, errorCode)