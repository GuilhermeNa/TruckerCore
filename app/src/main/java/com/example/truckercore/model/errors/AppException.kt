package com.example.truckercore.model.errors

open class AppException(message: String? = null, cause: Throwable? = null)
    : Exception(message, cause)