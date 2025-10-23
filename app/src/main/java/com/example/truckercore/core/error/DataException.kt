package com.example.truckercore.core.error

import com.example.truckercore.core.error.core.AppException

sealed class DataException(
    message: String? = null,
    cause: Throwable? = null
) : AppException(message, cause) {

    class Unknown(
        message: String = "An unknown error occurred.",
        cause: Throwable? = null
    ) : DataException(message, cause)

    class TooManyRequests(
        message: String = "Too many requests while sending email verification.",
        cause: Throwable? = null
    ) : DataException(message, cause)

    class Mapping(
        message: String = "An error occurred while mapping an object.",
        cause: Throwable? = null
    ) : InfraException(message, cause)

}