package com.example.truckercore.model.infrastructure.integration.exceptions.generic_ex

import com.example.truckercore.model.infrastructure.integration.exceptions.AppException

class NetworkException(
    message: String? = null,
    cause: Throwable? = null
): Exception(message, cause)