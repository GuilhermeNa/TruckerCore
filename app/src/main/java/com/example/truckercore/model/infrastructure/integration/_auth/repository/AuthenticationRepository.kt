package com.example.truckercore.model.infrastructure.integration._auth.repository

import com.example.truckercore.model.infrastructure.security.authentication.app_errors.AuthErrorCode
import com.example.truckercore.model.infrastructure.security.authentication.app_errors.AuthenticationAppErrorFactory
import com.example.truckercore.model.infrastructure.integration._auth.app_errors.AuthenticationAppException
import com.example.truckercore.model.infrastructure.security.authentication.app_errors.error_codes.NewEmailErrCode
import com.example.truckercore.model.infrastructure.integration._auth.app_errors.error_codes.ObserveEmailValidationErrCode
import com.example.truckercore.model.infrastructure.security.authentication.app_errors.error_codes.SendEmailVerificationErrCode
import com.example.truckercore.model.infrastructure.security.authentication.app_errors.error_codes.SignInErrCode
import com.example.truckercore.model.infrastructure.security.authentication.app_errors.error_codes.UpdateUserProfileErrCode
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import com.example.truckercore.model.shared.utils.sealeds.AppResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

/**
 * Interface responsible for handling user authentication operations via Firebase Authentication.
 *
 * Each method returns structured result types ([AppResponse] or [AppResult]) that encapsulate:
 * - Success with a result (e.g., [FirebaseUser])
 * - Failure via [AuthenticationAppException] containing an [AuthErrorCode]
 *
 * Errors are mapped using [AuthenticationAppErrorFactory], ensuring consistent handling across the application.
 */
interface AuthenticationRepository {

    /**
     * Creates a new Firebase user account using the provided email and password.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @return [AppResult.Success] containing the newly created [FirebaseUser]
     *
     * [AppResult.Error] with the appropriate [NewEmailErrCode] on failure
     *
     * Possible error codes:
     * - [NewEmailErrCode.UnsuccessfulTask] - When the user can not be recovered
     * - [NewEmailErrCode.InvalidCredentials] – Invalid email or weak password
     * - [NewEmailErrCode.AccountCollision] – Email is already registered
     * - [NewEmailErrCode.Network] – Network issue
     * - [NewEmailErrCode.Unknown] – Unexpected error
     *
     * ```kotlin
     * val result = repository.createUserWithEmail("user@example.com", "SecurePass123")
     * when (result) {
     *     is AppResult.Success -> {
     *          val fbUser: FirebaseUser = result.data
     *          // new account registered for user@example.com
     *     }
     *     is AppResult.Error -> {
     *          val message = result. exception.message
     *          // Show error message to the user
     *     }
     * }
     * ```
     */
    suspend fun createUserWithEmail(email: String, password: String): AppResult<FirebaseUser>

    /**
     * Sends an email verification message to the given Firebase user.
     *
     * @return [AppResult.Success] if the email was successfully sent
     *
     * [AppResult.Error] with a [SendEmailVerificationErrCode] if an error occurred.
     *
     * Possible error codes:
     * - [SendEmailVerificationErrCode.UserNotFound] - There is no logged user
     * - [SendEmailVerificationErrCode.UnsuccessfulTask] – Task failed unexpectedly
     * - [SendEmailVerificationErrCode.Unknown] – Unknown error occurred
     *
     * ```kotlin
     * val result = repository.sendEmailVerification()
     * when (result) {
     *     is AppResult.Success -> {
     *         // Inform the user to check their inbox
     *     }
     *     is AppResult.Error -> {
     *         val message = result.exception.message
     *         // Show error message to the user
     *     }
     * }
     * ```
     */
    suspend fun sendEmailVerification(): AppResult<Unit>

    /**
     * Updates the profile of the currently authenticated user (e.g., name, photo URL).
     *
     * @param profile A [UserProfileChangeRequest] containing the desired profile changes.
     * @return [AppResult.Success] if the profile was successfully updated
     *
     * [AppResult.Error] with [UpdateUserProfileErrCode] on failure.
     *
     * Possible error codes:
     * - [UpdateUserProfileErrCode.Network] - Network failure
     * - [UpdateUserProfileErrCode.UserNotFound] – There is no user in session
     * - [UpdateUserProfileErrCode.UnsuccessfulTask] – Task failed unexpectedly without throwing a specific exception
     * - [UpdateUserProfileErrCode.Unknown] – Unexpected error occurred
     *
     * ```kotlin
     * val result = repository.updateUserProfile(user, request)
     * when (result) {
     *     is AppResult.Success -> // name updated
     *     is AppResult.Error -> // failed in updating name
     * }
     * ```
     */
    suspend fun updateUserProfile(profile: UserProfileChangeRequest): AppResult<Unit>

    /**
     * Authenticates a user using their email and password credentials.
     *
     * @param email The user's email.
     * @param password The user's password.
     * @return [AppResult.Success] if login was successful
     *
     * [AppResult.Error] with [SignInErrCode] on failure.
     *
     * Possible error codes:
     * - [SignInErrCode.InvalidCredentials]
     * - [SignInErrCode.NetworkError]
     * - [SignInErrCode.TooManyRequests]
     * - [SignInErrCode.UnsuccessfulTask]
     * - [SignInErrCode.UnknownError]
     *
     * ```kotlin
     * val result = repository.signIn("user@example.com", "MyPassword")
     * when (result) {
     *     is AppResult.Success -> // start a new firebase session
     *     is AppResult.Error -> // login failed
     * }
     * ```
     */
    suspend fun signIn(email: String, password: String): AppResult<Unit>

    /**
     * Signs out the currently authenticated user.
     *
     * This action is synchronous and clears the local authentication session.
     *
     */
    fun signOut()

    /**
     * Retrieves the currently authenticated Firebase user, if available.
     *
     * @return The currently logged-in [FirebaseUser], or null if no user is authenticated.
     *
     * ```kotlin
     * val currentUser = repository.getCurrentUser()
     * if (currentUser != null) {
     *     // User is logged in
     * }
     * ```
     */
    fun getCurrentUser(): FirebaseUser?

    /**
     * Observes whether the authenticated user's email has been verified.
     *
     * Continuously checks the verification status in a coroutine-safe flow.
     *
     * @return [AppResult.Success] if the email has been verified
     *
     * [AppResponse.Error] with [ObserveEmailValidationErrCode] on failure
     *
     * Possible error codes:
     *
     * - [ObserveEmailValidationErrCode.UserNotFound] If the logged user is not found.
     *
     * **Example:**
     * ```kotlin
     * repository.observeEmailValidation().collect { response ->
     *     when (response) {
     *         is AppResult.Success -> // email verified
     *         is AppResult.Error -> // handle error
     *     }
     * }
     * ```
     */
    suspend fun observeEmailValidation(): AppResult<Unit>

}