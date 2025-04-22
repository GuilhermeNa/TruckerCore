package com.example.truckercore.model.infrastructure.integration._writer.for_api.exceptions

class InvalidInstructionException(
    message: String? = null,
    cause: Throwable? = null
) : InstructionExecutorException(message, cause)