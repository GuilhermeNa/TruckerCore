package com.example.truckercore.model.infrastructure.integration.writer.for_app.app_exception.error_codes

import com.example.truckercore.model.infrastructure.integration.writer.for_app.app_exception.ExecutorErrCode

/**
 * Sealed class representing error codes related to instruction execution operations.
 *
 * Implementations of this sealed class provide:
 * - `name`: A unique identifier for the error.
 * - `userMessage`: A user-friendly message that can be shown to the end user (in Portuguese).
 * - `logMessage`: A message that will be logged for internal tracking and debugging.
 * - `isRecoverable`: A boolean indicating if the error is recoverable (i.e., whether the operation can be retried).
 */
sealed class ExecuteInstructionErrCode : ExecutorErrCode {

    /**
     * Error indicating that the instruction received is malformed, invalid,
     * or violates required preconditions.
     */
    data object InvalidInstruction : ExecuteInstructionErrCode() {
        override val name = "INVALID_INSTRUCTION"
        override val userMessage = "As instruções recebidas são inválidas."
        override val logMessage = "Invalid instruction found during write process."
        override val isRecoverable = true
    }

    /**
     * Error indicating that the instruction is recognized but not yet implemented
     * in the back-end interpreter.
     */
    data object InstructionNotImplemented : ExecuteInstructionErrCode() {
        override val name = "INSTRUCTION_NOT_IMPLEMENTED"
        override val userMessage = "Instrução não reconhecida pelo servidor."
        override val logMessage = "The instruction is not implemented yet in back-end interpreter."
        override val isRecoverable = true
    }

    /**
     * Error indicating that an unexpected or unknown issue occurred during
     * the instruction execution process.
     */
    data object Unknown : ExecuteInstructionErrCode() {
        override val name = "UNKNOWN_ERROR"
        override val userMessage = "Erro desconhecido. Tente novamente."
        override val logMessage = "Unexpected error during data writing."
        override val isRecoverable = false
    }

}