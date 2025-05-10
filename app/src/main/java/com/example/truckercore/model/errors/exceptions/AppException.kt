package com.example.truckercore.model.errors.exceptions

abstract class AppException(message: String? = null, cause: Throwable? = null)
    : Exception(message, cause)
