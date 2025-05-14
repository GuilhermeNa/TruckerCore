package com.example.truckercore.model.infrastructure.integration.auth.for_app.repository

import com.example.truckercore._utils.classes.AppResponse
import com.example.truckercore._utils.classes.AppResult
import com.example.truckercore._utils.classes.Email
import com.example.truckercore._utils.classes.Password

/**
 * Repository interface that defines authentication-related operations used by the application layer.
 *
 * This interface serves as the application-level abstraction over authentication logic, typically implemented
 * using a remote backend such as Firebase. It exposes all the common user authentication workflows including
 * account creation, login, profile updates, and email verification.
 *
 * All methods return an [AppResult], which encapsulates either a success or an error,
 * making error handling explicit and consistent.
 */
interface AuthenticationRepository {

    /**
     * Creates a new user account using the given email and password.
     *
     * @param email The user's email address for registration.
     * @param password The password to associate with the new account.
     * @return [AppResult] wrapping `Unit` on success or an [AuthenticationAppException] on failure.
     *
     * ### Example:
     * ```
     * val result = authRepository.createUserWithEmail(email, password)
     * when (result) {
     *     is AppResult.Success -> { /* Account created successfully */ }
     *     is AppResult.Failure -> { /* Handle result.error */ }
     * }
     * ```
     */
    suspend fun createUserWithEmail(email: Email, password: Password): AppResult<Unit>

    /**
     * Sends an email verification to the currently authenticated user.
     *
     * @return [AppResult] wrapping `Unit` on success or an [AuthenticationAppException] on failure.
     *
     * ### Example:
     * ```
     * val result = authRepository.sendEmailVerification()
     * when (result) {
     *     is AppResult.Success -> { /* Email sent successfully */ }
     *     is AppResult.Failure -> { /* Handle result.error */ }
     * }
     * ```
     */
    suspend fun sendEmailVerification(): AppResult<Unit>

    /**
     * Signs in the user with the provided email and password.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @return [AppResult] wrapping `Unit` on success or an [AuthenticationAppException] on failure.
     *
     * ### Example:
     * ```
     * val result = authRepository.signIn(email, password)
     * when (result) {
     *     is AppResult.Success -> { /* User signed in */ }
     *     is AppResult.Failure -> { /* Handle result.error */ }
     * }
     * ```
     */
    suspend fun signIn(email: Email, password: Password): AppResult<Unit>

    /**
     * Observes and validates the user's email verification status.
     *
     * This method can be used to wait for the user's email to be verified after registration.
     *
     * @return [AppResult] wrapping `Unit` when the email is verified or an [AuthenticationAppException] on failure.
     *
     * ### Example:
     * ```
     * val result = authRepository.observeEmailValidation()
     * when (result) {
     *     is AppResult.Success -> { /* Email verified */ }
     *     is AppResult.Failure -> { /* Handle result.error */ }
     * }
     * ```
     */
    suspend fun observeEmailValidation(): AppResult<Unit>

    /**
     * Signs out the currently authenticated user.
     *
     * This operation is typically synchronous and does not return a result.
     *
     * ### Example:
     * ```
     * authRepository.signOut()
     * // User is now signed out
     * ```
     */
    fun signOut()

    fun thereIsLoggedUser(): Boolean

    fun getUserEmail(): AppResponse<Email>

    fun isEmailVerified(): Boolean

    suspend fun sendPasswordResetEmail(email: Email): AppResult<Unit>

}