package com.example.truckercore.model.errors.exceptions.domain

import com.example.truckercore.model.errors.exceptions.AppException

sealed class DomainException(message: String? = null, cause: Throwable? = null) :
    AppException(message, cause) {

    class InvalidForCreation(message: String? = null, cause: Throwable? = null) :
        DomainException(message, cause)

    class RuleViolated(message: String? = null, cause: Throwable? = null) :
        DomainException(message, cause)

    class Unknown(message: String? = null, cause: Throwable? = null) :
        DomainException(message, cause)

}

