package com.example.truckercore.core.error.core

open class AppException(
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause)