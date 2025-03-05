package com.example.truckercore.infrastructure.security.authentication.service

import com.example.truckercore.infrastructure.security.authentication.entity.Credentials
import com.example.truckercore.infrastructure.security.authentication.entity.LoggedUserDetails
import com.example.truckercore.infrastructure.security.authentication.entity.NewAccessRequirements
import com.example.truckercore.shared.utils.sealeds.Response
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
    fun authenticateCredentials(credentials: Credentials): Flow<Response<String>>

    /**
     * Signs in a user using their credentials.
     *
     * This method performs the sign-in process using the provided [Credentials] and returns a flow
     * with a [Response] indicating success (Unit) or failure (error response).
     *
     * @param credentials The credentials of the user, including email and password.
     * @return A [Flow] emitting the [Response] containing [Unit] on successful sign-in, or an error if it fails.
     */
    fun signIn(credentials: Credentials): Flow<Response<Unit>>

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
     * Creates a new system access based on the provided access requirements.
     *
     * This method processes the provided [NewAccessRequirements] and returns a flow
     * with a [Response] indicating the success (Unit) or failure (error response) of the access creation.
     *
     * @param requirements The requirements for creating a new system access.
     * @return A [Flow] emitting the [Response] containing [Unit] on success, or an error response if access creation fails.
     */
    fun createNewSystemAccess(requirements: NewAccessRequirements): Flow<Response<Unit>>

    /**
     * Retrieves the logged-in user along with their associated person details.
     *
     * @return A [Flow] emitting:
     * - [Response.Success] containing [LoggedUserDetails] if successful, or an error response if the user is not logged in.
     * - [Response.Error] if any error occurs.
     */
    fun getLoggedUserDetails(): Flow<Response<LoggedUserDetails>>

}