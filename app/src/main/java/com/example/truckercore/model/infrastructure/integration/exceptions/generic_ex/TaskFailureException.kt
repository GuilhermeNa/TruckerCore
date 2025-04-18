package com.example.truckercore.model.infrastructure.integration.exceptions.generic_ex

class TaskFailureException(
    message: String? = null,
    cause: Throwable? = null
): Exception(message,cause)