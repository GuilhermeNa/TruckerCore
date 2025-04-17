package com.example.truckercore.model.infrastructure.data_source.firebase._auth

import com.example.truckercore.model.infrastructure.data_source.firebase.exceptions.IncompleteTaskException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

/**
 * Interface responsible for interacting directly with Firebase Authentication SDK operations.
 *
 * This layer exposes raw Firebase behaviors, returning primitive types
 * such as [FirebaseUser] or throwing exceptions directly.
 *
 * Suspend functions are wrapped in `suspendCoroutine` for coroutine compatibility.
 */
interface FirebaseAuthDataSource {

    /**
     * Creates a new Firebase user with the specified email and password.
     *
     * @param email The user's email address.
     * @param password The user's chosen password.
     * @return The newly created [FirebaseUser] if the operation is successful.
     *
     * @throws IncompleteTaskException If Firebase returns a null user unexpectedly.
     * @throws FirebaseAuthWeakPasswordException If the password provided is considered too weak.
     * @throws FirebaseAuthInvalidCredentialsException If the email format is invalid or the credentials are malformed.
     * @throws FirebaseAuthUserCollisionException If the email is already associated with an existing Firebase account.
     * @throws FirebaseNetworkException If the operation fails due to a network connectivity issue.
     * Example:
     * ```kotlin
     * try {
     *     val user = dataSource.createUserWithEmail("user@example.com", "password123")
     *     // Successfully registered
     * } catch (e: Exception) {
     *     // Handle some Exception
     * }
     * ```
     */
    suspend fun createUserWithEmail(email: String, password: String): FirebaseUser

    /**
     * Sends an email verification message to the currently authenticated Firebase user.
     *
     * @throws NullFirebaseUserException If no user is logged in.
     * @throws IncompleteTaskException If the Firebase task completes unsuccessfully without throwing a specific exception.
     *
     * Example:
     * ```kotlin
     * try {
     *     dataSource.sendEmailVerification()
     *     // Email sent
     * } catch (e: Exception) {
     *     // Handle error
     * }
     * ```
     */
    suspend fun sendEmailVerification()

    /**
     * Updates the profile of the currently authenticated Firebase user.
     *
     * @param profile A [UserProfileChangeRequest] with the updated profile information.
     *
     * @throws NullFirebaseUserException If no user is currently authenticated.
     * @throws FirebaseNetworkException If the request fails due to a network connectivity issue.
     * @throws IncompleteTaskException If the Firebase task completes unsuccessfully without throwing a specific exception.
     *
     * Example:
     * ```kotlin
     * try {
     *     dataSource.updateUserProfile(profileUpdate)
     *     // Profile updated
     * } catch (e: Exception) {
     *     // Handle error
     * }
     * ```
     */
    suspend fun updateUserProfile(profile: UserProfileChangeRequest)

    /**
     * Signs in an existing Firebase user with the provided email and password.
     *
     * @param email The user's email address.
     * @param password The user's password.
     *
     * @throws FirebaseAuthInvalidUserException If the user corresponding to the email does not exist or has been disabled.
     * @throws FirebaseAuthInvalidCredentialsException If the credential is incorrect.
     * @throws FirebaseNetworkException If the request fails due to network issues.
     * @throws FirebaseTooManyRequestsException If access to this method is temporarily blocked due to many failed login attempts.
     * @throws IncompleteTaskException If the Firebase task completes unsuccessfully without throwing a specific exception.
     *
     * Example:
     * ```kotlin
     * try {
     *     dataSource.signInWithEmail("user@example.com", "password123")
     *     // User signed in
     * } catch (e: Exception) {
     *     // Handle sign-in error
     * }
     * ```
     */
    suspend fun signInWithEmail(email: String, password: String)

    /**
     * Signs out the currently authenticated user.
     *
     * This operation is synchronous and clears the current session locally.
     *
     */
    fun signOut()

    /**
     * Retrieves the currently authenticated Firebase user.
     *
     * @return The [FirebaseUser] if authenticated, or null otherwise.
     */
    fun getCurrentUser(): FirebaseUser?

    /**
     * Suspends until the currently authenticated user's email is verified.
     *
     * Internally polls the user's status using `reload()` every second until `isEmailVerified` returns true.
     *
     * @throws NullFirebaseUserException If no user is authenticated.
     *
     * Example:
     * ```kotlin
     * try {
     *     dataSource.observeEmailValidation()
     *     // Email has been verified
     * } catch (e: Exception) {
     *     // Handle timeout, null user, or Firebase error
     * }
     * ```
     */
    suspend fun observeEmailValidation()

}