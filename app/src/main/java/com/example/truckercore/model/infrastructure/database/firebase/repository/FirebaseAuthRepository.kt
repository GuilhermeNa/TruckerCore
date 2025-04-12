package com.example.truckercore.model.infrastructure.database.firebase.repository

import com.example.truckercore.model.infrastructure.security.authentication.errors.AuthErrorCode
import com.example.truckercore.model.infrastructure.security.authentication.errors.AuthErrorFactory
import com.example.truckercore.model.infrastructure.security.authentication.errors.AuthenticationException
import com.example.truckercore.model.infrastructure.security.authentication.errors.NewEmailErrCode
import com.example.truckercore.model.infrastructure.security.authentication.errors.ObserveEmailValidationErrCode
import com.example.truckercore.model.infrastructure.security.authentication.errors.SendEmailVerificationErrCode
import com.example.truckercore.model.infrastructure.security.authentication.errors.SignInErrCode
import com.example.truckercore.model.infrastructure.security.authentication.errors.UpdateUserProfileErrCode
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import com.example.truckercore.model.shared.utils.sealeds.AppResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.Flow

/**
 * Interface responsible for handling user authentication operations via Firebase Authentication.
 *
 * Each method returns structured result types ([AppResponse] or [AppResult]) that encapsulate:
 * - Success with a result (e.g., [FirebaseUser])
 * - Failure via [AuthenticationException] containing an [AuthErrorCode]
 *
 * Errors are mapped using [AuthErrorFactory], ensuring consistent handling across the application.
 */
interface FirebaseAuthRepository {

    /**
     * Creates a new Firebase user account using the provided email and password.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @return [AppResponse.Success] containing the newly created [FirebaseUser], or
     *         [Response.Error] with the appropriate [NewEmailErrCode] on failure.
     *
     * Possible error codes:
     * - [NewEmailErrCode.InvalidCredentials] – Invalid email or weak password
     * - [NewEmailErrCode.AccountCollision] – Email is already registered
     * - [NewEmailErrCode.Network] – Network issue
     * - [NewEmailErrCode.Unknown] – Unexpected error
     *
     * ```kotlin
     * val result = repository.createUserWithEmail("user@example.com", "SecurePass123")
     * when (result) {
     *     is AppResponse.Success -> {
     *          val fbUser: FirebaseUser = result.data
     *          // new account registered for user@example.com
     *     }
     *     is AppResponse.Empty -> {
     *           // account created but FirebaseUser was not recovered
     *     }
     *     is AppResponse.Error ->{
     *          val message = result. exception.message
     *          // Show error message to the user
     *     }
     * }
     * ```
     */
    suspend fun createUserWithEmail(email: String, password: String): AppResponse<FirebaseUser>

    /**
     * Sends an email verification message to the given Firebase user.
     *
     * @param firebaseUser The currently authenticated user.
     * @return [AppResult.Success] if the email was successfully sent, or [AppResult.Error] with a [SendEmailVerificationErrCode] if an error occurred.
     *
     * Possible error codes:
     * - [SendEmailVerificationErrCode.UnsuccessfulTask] – Task failed unexpectedly
     * - [SendEmailVerificationErrCode.Unknown] – Unknown error occurred
     *
     * ```kotlin
     * val result = repository.sendEmailVerification(user)
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
    suspend fun sendEmailVerification(firebaseUser: FirebaseUser): AppResult<Unit>

    /**
     * Updates the profile of the currently authenticated user (e.g., name, photo URL).
     *
     * @param fbUser The authenticated Firebase user.
     * @param profile A [UserProfileChangeRequest] containing the desired profile changes.
     * @return [AppResult.Success] if the profile was successfully updated, or [AppResult.Error] with [UpdateUserProfileErrCode] on failure.
     *
     * Possible error codes:
     * - [UpdateUserProfileErrCode.UnsuccessfulTask]
     * - [UpdateUserProfileErrCode.Unknown]
     *
     * ```kotlin
     * val result = repository.updateUserProfile(user, request)
     * if (result is AppResult.Success) {
     *           // name updated
     * } else {
     *           // failed in updating name
     * }
     * ```
     */
    suspend fun updateUserProfile(
        fbUser: FirebaseUser,
        profile: UserProfileChangeRequest
    ): AppResult<Unit>

    /**
     * Authenticates a user using their email and password credentials.
     *
     * @param email The user's email.
     * @param password The user's password.
     * @return [AppResult.Success] if login was successful, or [AppResult.Error] with [SignInErrCode] on failure.
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
     * if (result is AppResult.Success) {
     *     // proceed to app
     * } else {
     *     // show error message
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
     * @return A [Flow] emitting:
     * - [AppResponse.Success] if the email has been verified
     * - [AppResponse.Empty] if the email is not verified yet
     * - [AppResponse.Error] with [ObserveEmailValidationErrCode] on failure
     *
     * ```kotlin
     * repository.observeEmailValidation().collect { response ->
     *     when (response) {
     *         is AppResponse.Success -> // email verified
     *         is AppResponse.Empty -> // not verified yet
     *         is AppResponse.Error -> // handle error
     *     }
     * }
     * ```
     */
    fun observeEmailValidation(): Flow<AppResponse<Unit>>

}