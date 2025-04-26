package com.example.truckercore.model.infrastructure.integration.writer.for_app.app_errors

import com.example.truckercore.model.infrastructure.app_exception.ErrorFactory
import com.example.truckercore.model.infrastructure.integration.writer.for_api.exceptions.InstructionNotImplementedException
import com.example.truckercore.model.infrastructure.integration.writer.for_api.exceptions.InvalidInstructionException
import com.example.truckercore.model.infrastructure.integration.writer.for_app.app_errors.error_codes.ExecuteInstructionErrCode

/**
 * Factory responsible for mapping executor-layer exceptions into domain-specific error codes ([ExecuteInstructionErrCode]).
 *
 * This factory ensures that all instruction execution errors are consistently translated into
 * structured exceptions with:
 * - A specific error code ([ExecuteInstructionErrCode])
 * - A developer-oriented log message
 * - A preserved throwable cause for debugging
 *
 * The resulting [ExecutorAppException] objects help maintain unified error handling across
 * UI, logging, and monitoring systems during instruction execution workflows.
 */
class ExecutorAppErrorFactory : ErrorFactory {

    /**
     * Handles exceptions thrown during instruction execution.
     *
     * Maps known exceptions to specific [ExecuteInstructionErrCode] values.
     *
     * @param t The throwable captured during the execution process.
     * @return An [ExecutorAppException] with the corresponding [ExecuteInstructionErrCode].
     *
     * Possible error codes:
     * - [ExecuteInstructionErrCode.InstructionNotImplemented] – When the instruction is recognized but not yet implemented
     * - [ExecuteInstructionErrCode.InvalidInstruction] – When the instruction is malformed, incomplete, or violates preconditions
     * - [ExecuteInstructionErrCode.Unknown] – When an unrecognized or unexpected exception occurs
     */
    operator fun invoke(t: Throwable): ExecutorAppException {
        val code = when (t) {
            is InstructionNotImplementedException -> ExecuteInstructionErrCode.InstructionNotImplemented
            is InvalidInstructionException -> ExecuteInstructionErrCode.InvalidInstruction
            else -> ExecuteInstructionErrCode.Unknown
        }

        factoryLogger(code)

        return ExecutorAppException(
            message = code.logMessage,
            errorCode = code,
            cause = t
        )
    }

}