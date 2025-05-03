package com.example.truckercore.model.infrastructure.integration.writer.for_app.app_errors

import com.example.truckercore.model.errors.AppException
import com.example.truckercore.model.errors.ErrorCode
import com.example.truckercore.model.infrastructure.integration.writer.for_app.app_errors.error_codes.ExecuteInstructionErrCode

/**
 * Exception class used to represent domain-specific errors in the instruction execution layer.
 *
 * This exception wraps:
 * - A user-friendly message (optional)
 * - A technical cause ([Throwable]), for stack traces or debugging (optional)
 * - A domain-specific [ErrorCode] indicating the nature of the failure
 *
 * This class enables consistent error handling and logging for all instruction-related
 * operations, including validation, dispatching, and backend interpretation.
 *
 * @param message Optional message that can be displayed or logged.
 * @param cause Optional cause for the exception, useful for debugging.
 * @param errorCode A specific [ErrorCode] that categorizes the failure (e.g., [ExecuteInstructionErrCode]).
 */
class ExecutorAppException(
    message: String? = null,
    cause: Throwable? = null,
    errorCode: ExecutorErrCode
) : AppException(message, cause, errorCode)