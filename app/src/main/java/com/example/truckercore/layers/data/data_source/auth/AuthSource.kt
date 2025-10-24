package com.example.truckercore.layers.data.data_source.auth

import com.example.truckercore.core.error.core.ErrorMapper
import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.core.my_lib.classes.Password
import com.example.truckercore.core.my_lib.files.ONE_SEC

/**
 * Abstract interface for handling authentication operations with a remote backend (e.g., Firebase).
 *
 * This abstraction layer hides the details of the underlying authentication provider
 * and provides a unified contract to the application.
 *
 * All exceptions are mapped to [AuthSourceException] types to prevent the app layer from depending
 * on backend-specific exceptions.
 *
 * @property errorMapper Maps exceptions thrown by the backend to domain-specific `AuthSourceException`s.
 */
abstract class AuthSource(protected val errorMapper: ErrorMapper) {

    /**
     * Creates a new user account using the provided email and password.
     *
     * ### Example:
     * ```
     * try {
     *     authSource.createUserWithEmail(email, password)
     *     // operation succeed
     * } catch (e: Exception) {
     *     // handle operation error
     * }
     * ```
     *
     * @param email The email address used for registration.
     * @param password The password associated with the new user.
     * @throws AuthSourceException If user creation fails due to weak password, collision, network, etc.
     */
    abstract suspend fun createUserWithEmail(email: Email, password: Password)

    /**
     * Sends a verification email to the currently authenticated user.
     *
     * ### Example:
     * ```
     * try {
     *     authSource.sendEmailVerification()
     *     // operation succeed
     * } catch (e: Exception) {
     *     // handle operation error
     * }
     * ```
     *
     * @throws AuthSourceException If the user is not logged in or sending fails.
     */
    abstract suspend fun sendEmailVerification()

    /**
     * Signs in an existing user with the given email and password.
     *
     * ### Example:
     * ```
     * try {
     *     authSource.signInWithEmail(email, password)
     *     // operation succeed
     * } catch (e: Exception) {
     *     // handle operation error
     * }
     * ```
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @throws AuthSourceException If credentials are invalid or network issues occur.
     */
    abstract suspend fun signInWithEmail(email: Email, password: Password)

    /**
     * Observes email validation state and suspends until the user's email is verified.
     *
     * ### Example:
     * ```
     * try {
     *     authSource.observeEmailValidation(email, password)
     *     // operation succeed
     * } catch (e: Exception) {
     *     // handle operation error
     * }
     * ```
     *
     * @throws AuthSourceException If the session is inactive or an unexpected error occurs.
     */
    abstract suspend fun observeEmailValidation(refreshTime: Long = ONE_SEC)

    /**
     * Signs out the currently authenticated user.
     */
    abstract fun signOut()

    abstract fun thereIsLoggedUser(): Boolean

    abstract fun getUserEmail(): String?

    abstract fun isEmailVerified(): Boolean

    abstract suspend fun sendPasswordResetEmail(email: Email)

    abstract fun getUid(): String

}