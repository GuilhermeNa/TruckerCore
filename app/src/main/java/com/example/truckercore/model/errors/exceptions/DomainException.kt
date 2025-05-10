package com.example.truckercore.model.errors.exceptions

sealed class DomainException(message: String? = null, cause: Throwable? = null) :
    AppException(message, cause) {

    class RuleViolated(message: String? = null, cause: Throwable? = null) :
        DomainException(message, cause)

    class Unknown(message: String? = null, cause: Throwable? = null) :
        DomainException(message, cause)

}

