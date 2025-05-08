package com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api.exceptions

/**
 * Exception indicating that the received instruction is invalid, malformed,
 * or violates expected preconditions during execution.
 *
 * Common causes include:
 * - Missing or incorrect instruction parameters
 * - Incompatible or unsupported instruction format
 * - Business rule violations at the instruction level
 *
 * This exception is typically thrown by the instruction validation layer
 * before dispatching to the executor or interpreter.
 *
 * @param message Optional message describing the specific validation issue.
 * @param cause Optional cause of the exception, useful for debugging.
 */
class InvalidInstructionException(
    message: String? = null,
    cause: Throwable? = null
) : InstructionExecutorException(message, cause)