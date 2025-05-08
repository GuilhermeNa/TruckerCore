package com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api.exceptions

/**
 * Exception indicating that the given instruction is recognized, but not yet
 * implemented by the execution or interpretation logic.
 *
 * This exception is useful for signaling that the instruction type is valid,
 * but currently unsupported in the backend or interpreter implementation.
 *
 * @param message Optional message describing the unsupported instruction.
 * @param cause Optional cause of the exception, useful for debugging.
 */
class InstructionNotImplementedException(
    message: String? = null,
    cause: Throwable? = null
) : InstructionExecutorException(message, cause)