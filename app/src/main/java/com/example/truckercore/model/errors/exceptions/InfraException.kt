package com.example.truckercore.model.errors.exceptions

sealed class InfraException(message: String?, cause: Throwable? = null) :
    AppException(message, cause) {

    class NetworkUnavailable :
        InfraException(message = "Network is unavailable")

    class WriterError(message: String? = null, cause: Throwable? = null) :
        InfraException(message, cause)

    class DatabaseError(message: String? = null, cause: Throwable? = null) :
        InfraException(message, cause)

    class AuthError(message: String? = null, cause: Throwable? = null) :
        InfraException(message, cause)

}