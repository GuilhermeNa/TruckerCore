package com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.exceptions

/**
 * Custom exception class used to represent errors that occur during the execution or processing of instructions.
 * @param message Optional message providing additional information about the error.
 */
class InvalidInstructionException(message: String? = null): Exception(message)