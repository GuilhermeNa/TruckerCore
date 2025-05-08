package com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.instruction.exceptions

/**
 * Custom exception class used to represent errors that occur during the execution or processing of instructions.
 * @param message Optional message providing additional information about the error.
 */
class InstructionException(message: String? = null): Exception(message)