package com.example.truckercore.model.infrastructure.security.authentication.service

import com.example.truckercore.model.infrastructure.security.authentication.entity.Credentials
import com.example.truckercore.model.infrastructure.security.authentication.entity.SessionInfo
import com.example.truckercore.model.infrastructure.security.authentication.entity.NewAccessRequirements
import com.example.truckercore.model.shared.utils.sealeds.Response
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the authentication service.
 * Provides methods for authenticating user credentials, signing in, signing out,
 * retrieving the current authenticated user, and creating new system access.
 */
interface AuthService {

    /**
     * Authenticates a user using their credentials.
     *
     * This method validates the provided [Credentials] and returns a flow that emits a response
     * containing an authentication token (String) if successful, or an error response if authentication fails.
     *
     * @param credentials The credentials of the user, including email and password.
     * @return A [Flow] emitting the [Response] with the authentication token, or an error if authentication fails.
     */
    fun createUserWithEmailAndPassword(credentials: Credentials): Flow<Response<String>>

    /**
     * Authenticates a user using their phone number and authentication credentials.
     *
     * This method validates the provided [PhoneAuthCredential] and returns a flow that emits a response
     * containing an authentication token (String) if successful, or an error response if authentication fails.
     *
     * @param phoneAuthCredential The authentication credentials for the user's phone number, including verification ID and code.
     * @return A [Flow] emitting the [Response] with the authentication token, or an error if authentication fails.
     */
    fun createUserWithPhone(phoneAuthCredential: PhoneAuthCredential): Flow<Response<String>>

    /**
     * Signs in a user using their credentials (email and password).
     *
     * This method performs the sign-in process by utilizing the provided [Credentials],
     * authenticating the user via Firebase, and then fetching the corresponding logged user
     * details. The method returns a [Flow] emitting a [Response] that contains:
     * - [SessionInfo] on successful sign-in with user details.
     * - [Response.Empty] if the user is authenticated but no details are found.
     * - An error response if the sign-in or user retrieval fails.
     *
     * @param credentials The credentials of the user, including email and password.
     * @return A [Flow] emitting a [Response] containing:
     * - [SessionInfo] on successful sign-in,
     * - [Response.Empty] if no user details are found,
     * - An error if the process fails (e.g., authentication failure or network error).
     */
    fun signIn(credentials: Credentials): Flow<Response<SessionInfo>>

    /**
     * Signs out the current authenticated user.
     *
     * This method performs the sign-out operation and does not return any value.
     * It effectively logs the user out of the system.
     */
    fun signOut()

    /**
     * Checks if there is a currently logged-in user.
     *
     * This method verifies whether a user is logged into the system and returns a boolean indicating
     * the presence of a logged-in user. It checks if the `FirebaseUser` object is non-null.
     *
     * @return `true` if there is a logged-in user, `false` otherwise.
     */
    fun thereIsLoggedUser(): Boolean

     /**
     * Retrieves the logged-in user along with their associated person details.
     *
     * @return A [Flow] emitting:
     * - [Response.Success] containing [SessionInfo] if successful, or an error response if the user is not logged in.
     * - [Response.Error] if any error occurs.
     */
    fun getSessionInfo(): Flow<Response<SessionInfo>>

    /**
     * Creates a new system access based on the provided access requirements.
     *
     * This method processes the provided [NewAccessRequirements] and returns a flow
     * with a [Response] indicating the success (Unit) or failure (error response) of the access creation.
     *
     * @param requirements The requirements for creating a new system access.
     * @return A [Flow] emitting the [Response] containing [Unit] on success, or an error response if access creation fails.
     */
    fun createNewSystemAccess(requirements: NewAccessRequirements): Flow<Response<Unit>>

}