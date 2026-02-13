package com.example.truckercore.layers.data.data_source.auth

import com.example.truckercore.core.error.core.AppException
import com.example.truckercore.core.error.core.ErrorMapper
import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.core.my_lib.classes.Name
import com.example.truckercore.core.my_lib.classes.Password

/**
 * Abstract source for authentication operations.
 *
 * This class defines the contract for an authentication source in the application.
 * Implementations can use different backends (e.g., FirebaseAuth) but must adhere
 * to these behaviors.
 *
 * Implementations must handle mapping of backend-specific errors to domain or
 * application-specific exceptions via [errorMapper].
 */
abstract class AuthSource(protected val errorMapper: ErrorMapper) {

    /**
     * Creates a new user with the provided email and password.
     *
     * @param email The user's email wrapped in a domain type [Email].
     * @param password The user's password wrapped in a domain type [Password].
     *
     * @throws AppException if creation fails (e.g., weak password, network error, or collision).
     */
    abstract suspend fun createUserWithEmail(email: Email, password: Password)

    /**
     * Sends a verification email to the currently logged-in user.
     *
     * @throws AppException if sending fails (e.g., network error, user not logged in).
     */
    abstract suspend fun sendEmailVerification()

    /**
     * Signs in a user with the provided email and password.
     *
     * @param email The user's email.
     * @param password The user's password.
     *
     * @throws AppException if login fails (invalid credentials, network issues, etc.).
     */
    abstract suspend fun signInWithEmail(email: Email, password: Password)

    /**
     * Waits for email verification to complete.
     *
     * This method may be implemented as a blocking suspending function or a
     * reactive stream (e.g., Flow<Boolean>) in concrete implementations.
     *
     * @throws AppException if the operation fails.
     */
    abstract suspend fun waitEmailValidation()

    /**
     * Signs out the current user.
     *
     * Should clear all local session or cached authentication state.
     */
    abstract fun signOut()

    /**
     * Updates the display name of the currently logged-in user.
     *
     * @param name The new display name wrapped in a domain type [Name].
     *
     * @throws AppException if update fails (e.g., network error, user not logged in).
     */
    abstract suspend fun updateName(name: Name)

    /**
     * Checks if the currently logged-in user has a display name set.
     *
     * @return `true` if the display name is set, `false` otherwise.
     */
    abstract fun isNameDefined(): Boolean

    /**
     * Checks if a user is currently logged in.
     *
     * @return `true` if a user is logged in, `false` otherwise.
     */
    abstract fun hasLoggedUser(): Boolean

    /**
     * Returns the email of the currently logged-in user, if available.
     *
     * @return User email as a string, or `null` if not available.
     */
    abstract fun getUserEmail(): String?

    abstract fun getUserName(): String?

    /**
     * Checks if the currently logged-in user's email is verified.
     *
     * @return `true` if verified, `false` otherwise.
     */
    abstract fun isEmailVerified(): Boolean

    /**
     * Sends a password reset email to the provided email address.
     *
     * @param email The email to send the reset link to.
     *
     * @throws AppException if the operation fails (e.g., invalid user, network error).
     */
    abstract suspend fun sendPasswordResetEmail(email: Email)

    /**
     * Returns the UID of the currently logged-in user.
     *
     * @return UID as a string.
     *
     * @throws AppException if no user is logged in.
     */
    abstract fun getUid(): String

}