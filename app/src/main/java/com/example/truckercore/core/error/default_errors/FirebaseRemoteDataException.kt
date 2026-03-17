package com.example.truckercore.core.error.default_errors

class FirebaseRemoteDataException(
    message: String? = null, throwable: Throwable? = null
) : Exception(message, throwable)