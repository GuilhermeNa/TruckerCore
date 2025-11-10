package com.example.truckercore.core.error

import com.example.truckercore.core.error.core.AppException

sealed class DomainException(
    message: String? = null,
    cause: Throwable? = null
) : AppException(message, cause) {

    class Unknown(
        message: String = "An unknown error occurred.",
        cause: Throwable? = null
    ) : DomainException(message, cause)

    class UnauthorizedAccess(
        message: String = "User does not have the necessary permission to perform this action.",
        cause: Throwable? = null
    ) : DomainException(message, cause)

    class RuleViolation(
        message: String,
        cause: Throwable? = null
    ) : DomainException(message, cause)

    class UserNotFound(
        message: String = "No active user found.",
        cause: Throwable? = null
    ) : DomainException(message, cause)

    class WeakPassword(
        message: String = "The password provided is too weak.",
        cause: Throwable? = null
    ) : DomainException(message, cause)

    class InvalidCredentials(
        message: String = "The email address or password is invalid.",
        cause: Throwable? = null
    ) : DomainException(message, cause)

    class UserCollision(
        message: String = "An account already exists with this email address.",
        cause: Throwable? = null
    ) : DomainException(message, cause)

    class InvalidUser(
        message: String = "The user account was not found or may have been deleted.",
        cause: Throwable? = null
    ) : DomainException(message, cause)

}