package com.example.truckercore.model.errors.exceptions

sealed class InfraException(message: String? = null, cause: Throwable? = null) :
    AppException(message, cause) {

    class NetworkUnavailable : InfraException()

    class WriterError(message: String? = null, cause: Throwable? = null) :
        InfraException(message, cause)

    class DatabaseError(message: String? = null, cause: Throwable? = null) :
        InfraException(message, cause)

    class AuthError(message: String? = null, cause: Throwable? = null) :
        InfraException(message, cause)

}