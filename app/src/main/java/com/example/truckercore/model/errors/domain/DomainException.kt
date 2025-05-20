package com.example.truckercore.model.errors.domain

import com.example.truckercore.model.errors.AppException
import com.example.truckercore.model.errors.domain.error_code.RuleViolatedErrorCode

sealed class DomainException(message: String? = null, cause: Throwable? = null) :
    AppException(message, cause) {

    class InvalidForCreation(message: String? = null, cause: Throwable? = null) :
        DomainException(message, cause)

    class RuleViolated(
        val code: RuleViolatedErrorCode,
        message: String? = null,
        cause: Throwable? = null
    ) :
        DomainException(message, cause)

    class Unknown(message: String? = null, cause: Throwable? = null) :
        DomainException(message, cause)

}

