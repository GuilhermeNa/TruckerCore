package com.example.truckercore.core.error

import com.example.truckercore.core.error.core.AppException

sealed class PresentationException(
    message: String? = null,
    cause: Throwable? = null
): AppException(message, cause) {

    class Unknown(
        message: String = "An unknown error occurred.",
        cause: Throwable? = null
    ) : PresentationException(message, cause)

}