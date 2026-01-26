package com.example.truckercore.core.error

import com.example.truckercore.core.error.core.AppException

/**
 * Represents errors related to business rules and domain logic.
 *
 * These exceptions indicate violations of domain constraints
 * or invalid business operations.
 */
sealed class DomainException(
    message: String? = null,
    cause: Throwable? = null
) : AppException(message, cause) {

    /**
     * Represents an unknown domain error.
     */
    class Unknown(
        message: String = "An unknown error occurred.",
        cause: Throwable? = null
    ) : DomainException(message, cause)

    /**
     * Indicates that the user does not have permission
     * to perform the requested action.
     */
    class UnauthorizedAccess(
        message: String = "User does not have the necessary permission to perform this action.",
        cause: Throwable? = null
    ) : DomainException(message, cause)

    /**
     * Indicates a violation of a business rule.
     */
    class RuleViolation(
        message: String,
        cause: Throwable? = null
    ) : DomainException(message, cause)

    /**
     * Indicates that no active user could be found.
     */
    class UserNotFound(
        message: String = "No active user found.",
        cause: Throwable? = null
    ) : DomainException(message, cause)

    /**
     * Indicates that the provided password does not meet security requirements.
     */
    class WeakPassword(
        message: String = "The password provided is too weak.",
        cause: Throwable? = null
    ) : DomainException(message, cause)

    /**
     * Indicates that the provided credentials are invalid.
     */
    class InvalidCredentials(
        message: String = "The email address or password is invalid.",
        cause: Throwable? = null
    ) : DomainException(message, cause)

    /**
     * Indicates that a user account already exists for the given identifier.
     */
    class UserCollision(
        message: String = "An account already exists with this email address.",
        cause: Throwable? = null
    ) : DomainException(message, cause)

    /**
     * Indicates that the user account is invalid or no longer exists.
     */
    class InvalidUser(
        message: String = "The user account was not found or may have been deleted.",
        cause: Throwable? = null
    ) : DomainException(message, cause)

    /**
     * Indicates that the provided email is invalid.
     */
    class InvalidEmail(
        message: String = "Provided email is not valid.",
        cause: Throwable? = null
    ) : DomainException(message, cause)

    /**
     * Indicates that the provided password is invalid.
     */
    class InvalidPassword(
        message: String = "Provided password is not valid.",
        cause: Throwable? = null
    ) : DomainException(message, cause)

    /**
     * Indicates that the provided name is invalid.
     */
    class InvalidName(
        message: String = "Provided name is not valid.",
        cause: Throwable? = null
    ) : DomainException(message, cause)

}