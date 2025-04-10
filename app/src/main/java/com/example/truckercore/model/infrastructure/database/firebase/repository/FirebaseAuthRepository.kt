package com.example.truckercore.model.infrastructure.database.firebase.repository

import com.example.truckercore.model.shared.utils.sealeds.Response
import com.example.truckercore.model.shared.utils.sealeds.Result
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing a repository for Firebase Authentication operations.
 * Provides methods for user authentication, sign-in, sign-out, and fetching the current authenticated user.
 */
internal interface FirebaseAuthRepository {

    /**
     * Authenticates a user using their email and password.
     *
     * This method returns a flow that emits a response containing the authentication token (String)
     * if the authentication is successful, or an error response if authentication fails.
     *
     * @param email The email of the user trying to authenticate.
     * @param password The password of the user trying to authenticate.
     * @return A [Flow] emitting the [Response] with the authentication token, or an error if authentication fails.
     */
    suspend fun createUserWithEmail(email: String, password: String): Response<FirebaseUser>

    /**
     * Sends a verification email to the newly registered user.
     *
     * This method sends an email verification to the provided [FirebaseUser] after they have been successfully created.
     * Returns a [Response] indicating success or failure of the verification email sending.
     *
     * @param firebaseUser The user for whom the email verification will be sent.
     * @return A [Result] with successful email verification, or an error response if it fails.
     */
    suspend fun sendEmailVerification(firebaseUser: FirebaseUser): Result<Unit>

    /**
     * Updates the profile information of the given user.
     *
     * This method allows for updating the user's profile information, such as their display name or photo URL.
     * It uses the provided [UserProfileChangeRequest] to perform the update for the given [FirebaseUser].
     * Returns a [Response] indicating the success or failure of the profile update operation.
     *
     * @param fbUser The [FirebaseUser] whose profile is to be updated.
     * @param profile The [UserProfileChangeRequest] containing the updated profile information.
     * @return A [Result] with successful email verification, or an error response if it fails.
     */
    suspend fun updateUserProfile(fbUser: FirebaseUser, profile: UserProfileChangeRequest): Result<Unit>

    /**
     * Authenticates a user using their phone number.
     *
     * This method returns a flow that emits a response containing the authentication token (String)
     * if the authentication is successful, or an error response if authentication fails.
     *
     * @return A [Flow] emitting the [Response] with the authentication token, or an error if authentication fails.
     */
    suspend fun createUserWithPhone(credential: PhoneAuthCredential): Response<String>

    /**
     * Signs in a user with their email and password.
     *
     * This method performs the sign-in process and returns a [Flow] with a [Response] indicating success (Unit)
     * or failure (error response).
     *
     * @param email The email of the user trying to sign in.
     * @param password The password of the user trying to sign in.
     * @return A [Flow] emitting the [Response] containing [Unit] on successful sign-in or an error if it fails.
     */
    fun signIn(email: String, password: String): Flow<Response<Unit>>

    /**
     * Signs out the current authenticated user.
     *
     * This method does not return a value. It performs the sign-out operation on Firebase.
     */
    fun signOut()

    /**
     * Retrieves the current authenticated Firebase user.
     *
     * This method returns the current [FirebaseUser] if a user is authenticated, or null if no user is signed in.
     *
     * @return The current [FirebaseUser] or null if no user is authenticated.
     */
    fun getCurrentUser(): FirebaseUser?

    fun observeEmailValidation(): Flow<Response<Unit>>

}