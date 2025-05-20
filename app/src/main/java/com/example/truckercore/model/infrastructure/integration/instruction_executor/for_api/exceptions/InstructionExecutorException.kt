package com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api.exceptions

abstract class InstructionExecutorException(message: String? = null, cause: Throwable? = null) :
    Exception(message, cause)