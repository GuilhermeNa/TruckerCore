package com.example.truckercore.model.infrastructure.integration.exceptions.generic_ex

class UnknownException(
    message: String? = null,
    cause: Throwable? = null,
) : Exception(message, cause)