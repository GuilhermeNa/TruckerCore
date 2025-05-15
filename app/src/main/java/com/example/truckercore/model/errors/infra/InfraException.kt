package com.example.truckercore.model.errors.infra

import com.example.truckercore.model.errors.AppException
import com.example.truckercore.model.errors.infra.error_code.AuthErrorCode
import com.example.truckercore.model.errors.infra.error_code.DatabaseErrorCode

sealed class InfraException(message: String? = null, cause: Throwable? = null) :
    AppException(message, cause) {

    class NetworkUnavailable : InfraException()

    class WriterError(message: String? = null, cause: Throwable? = null) :
        InfraException(message, cause)

    class DatabaseError(
        val code: DatabaseErrorCode,
        message: String? = null,
        cause: Throwable? = null
    ) : InfraException(message, cause)

    class AuthError(
        val code: AuthErrorCode,
        message: String? = null,
        cause: Throwable? = null
    ) : InfraException(message, cause)

}