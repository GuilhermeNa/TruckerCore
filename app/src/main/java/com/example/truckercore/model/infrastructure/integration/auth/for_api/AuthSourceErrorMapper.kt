package com.example.truckercore.model.infrastructure.integration.auth.for_api

import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.AuthSourceException

/**
 * Contract for mapping backend-specific authentication exceptions into domain-specific exceptions.
 *
 * This interface serves as an abstraction layer for translating low-level exceptions thrown by
 * external authentication providers (such as Firebase) into application-level exceptions represented
 * by [AuthSourceException]. This ensures a clean separation between the backend implementation and
 * the app's domain logic.
 *
 * Implementations of this interface should examine the provided [Throwable] and return the appropriate
 * subclass of [AuthSourceException] that accurately describes the nature of the failure.
 *
 * ### Example usage:
 * ```
 * try {
 *     firebaseAuth.createUserWithEmailAndPassword(email, password)
 * } catch (e: Throwable) {
 *     val mappedError = errorMapper.creatingUserWithEmail(e)
 *     throw mappedError
 * }
 * ```
 */
interface AuthSourceErrorMapper {

    /**
     * Maps an exception thrown during the creation of a user with email and password.
     *
     * This method is used when an error occurs while creating a new user account. The mapped
     * exception will typically correspond to weak passwords, email collisions, invalid credentials,
     * or network issues.
     *
     * @param e The original exception thrown by the authentication provider.
     * @return A domain-specific [AuthSourceException] describing the error.
     */
    fun creatingUserWithEmail(e: Throwable): AuthSourceException

    /**
     * Maps an exception thrown during the process of sending a verification email.
     *
     * This method handles errors related to the inability to dispatch a verification email to the user,
     * which may happen due to session issues, task failures, or unknown backend problems.
     *
     * @param e The original exception thrown while sending the email verification.
     * @return A domain-specific [AuthSourceException] describing the error.
     */
    fun sendingEmailVerification(e: Throwable): AuthSourceException

    /**
     * Maps an exception thrown when attempting to update a user's profile.
     *
     * This method is used to interpret backend errors that occur while updating user information
     * such as display name or photo URL. Common causes include invalid session data, network
     * failures, or backend task failures.
     *
     * @param e The original exception thrown during the profile update operation.
     * @return A domain-specific [AuthSourceException] describing the error.
     */
    fun updatingProfile(e: Throwable): AuthSourceException

    /**
     * Maps an exception thrown during the sign-in process using email and password.
     *
     * This method is used when a user attempts to authenticate and the backend responds
     * with a failure. This might be due to invalid credentials, network problems,
     * account suspension, or excessive login attempts.
     *
     * @param e The original exception thrown while signing in.
     * @return A domain-specific [AuthSourceException] describing the error.
     */
    fun signingInWithEmail(e: Throwable): AuthSourceException

    fun sendPasswordResetEmail(e: Throwable): AuthSourceException

}
