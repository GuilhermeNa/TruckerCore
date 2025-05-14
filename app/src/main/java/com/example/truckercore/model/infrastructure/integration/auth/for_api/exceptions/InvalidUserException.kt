package com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions

class InvalidUserException(message: String? = null, cause: Throwable? = null) :
    AuthSourceException(message, cause)