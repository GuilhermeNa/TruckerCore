package com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api.exceptions

/**
 * Base exception type for all instruction execution-related errors.
 *
 * This abstract class serves as the root for all exceptions thrown during
 * the instruction execution process, including:
 * - Validation failures
 * - Unsupported or unimplemented instructions
 * - Runtime execution issues
 *
 * Subclasses should represent specific failure scenarios within the execution
 * domain, enabling fine-grained and consistent error handling throughout
 * the instruction lifecycle.
 *
 * @param message Optional message describing the error context.
 * @param cause Optional underlying exception cause, useful for debugging.
 */
abstract class InstructionExecutorException(
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause)