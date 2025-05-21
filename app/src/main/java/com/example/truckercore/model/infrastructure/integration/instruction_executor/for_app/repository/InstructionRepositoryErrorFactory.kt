package com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.repository

import com.example.truckercore._utils.expressions.getClassName
import com.example.truckercore.model.errors.infra.InfraException
import com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions.InterpreterException
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api.exceptions.ApiInstructionException
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.exceptions.InstructionException
import com.example.truckercore.model.logger.AppLogger

class InstructionRepositoryErrorFactory {

    operator fun invoke(e: Exception): InfraException.WriterError {
        val message = when (e) {
            is InstructionException -> INVALID_INSTRUCTION
            is InterpreterException -> INTERPRETER_ERROR
            is ApiInstructionException -> INVALID_API_INSTRUCTION
            else -> UNKNOWN_ERROR
        }

        logDebug(e)

        return InfraException.WriterError(
            message = message,
            cause = e
        )
    }

    private fun logDebug(e: Exception) {
        AppLogger.d(
            getClassName(),
            "$LOG_MESSAGE ${e::class.simpleName} - ${e.message}"
        )
    }

    companion object {

        private const val LOG_MESSAGE = "Handled exception of type:"

        private const val INVALID_INSTRUCTION =
            "The provided instruction is invalid or malformed. Please check the syntax or structure."

        private const val INTERPRETER_ERROR =
            "An error occurred while interpreting the instruction. There may be a mismatch between the instruction and the interpreter."

        private const val INVALID_API_INSTRUCTION =
            "The interpreted instruction is invalid for the target API. Validation or compatibility issues may be present."

        private const val UNKNOWN_ERROR =
            "An unexpected error occurred during instruction execution."

    }

}