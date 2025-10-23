package com.example.truckercore.core.error.core

abstract class AppException(
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause)