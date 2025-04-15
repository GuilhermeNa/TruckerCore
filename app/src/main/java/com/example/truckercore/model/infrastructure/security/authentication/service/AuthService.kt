package com.example.truckercore.model.infrastructure.security.authentication.service

import com.example.truckercore.model.infrastructure.security.authentication.entity.EmailAuthCredential
import com.example.truckercore.model.infrastructure.security.authentication.entity.NewAccessRequirements
import com.example.truckercore.model.infrastructure.security.authentication.entity.NewEmailResult
import com.example.truckercore.model.infrastructure.security.authentication.entity.SessionInfo
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.interfaces.CreateUserAndVerifyEmailUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.interfaces.ObserveEmailValidationUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.interfaces.SendVerificationEmailUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.interfaces.ThereIsLoggedUserUseCase
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import com.example.truckercore.model.shared.utils.sealeds.AppResult
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the authentication service.
 * Provides methods for authenticating user credentials, signing in, signing out,
 * retrieving the current authenticated user, and creating new system access.
 */
interface AuthService {

    /**
     * Invokes the use case to create a new user and send the verification email.
     * @see [CreateUserAndVerifyEmailUseCase.invoke]
     */
    suspend fun createUserAndVerifyEmail(credential: EmailAuthCredential): NewEmailResult

    /**
     * Invokes the use case to send a verification email to the current Firebase user.
     * @see SendVerificationEmailUseCase.invoke
     */
    suspend fun sendVerificationEmail(): AppResult<Unit>

    /**
     * Invokes the use case to observe the email validation.
     * @see ObserveEmailValidationUseCase.invoke
     */
    fun observeEmailValidation(): Flow<AppResult<Unit>>

    /**
     *  Checks if there is a currently logged-in user.
     * @see ThereIsLoggedUserUseCase.invoke
     */
    fun thereIsLoggedUser(): Boolean




}
    //--------------------------------------------
    /*    */
    /**
     * Signs in a user using their credentials (email and password).
     *
     * This method performs the sign-in process by utilizing the provided [EmailAuthCredential],
     * authenticating the user via Firebase, and then fetching the corresponding logged user
     * details. The method returns a [Flow] emitting a [AppResponse] that contains:
     * - [SessionInfo] on successful sign-in with user details.
     * - [AppResponse.Empty] if the user is authenticated but no details are found.
     * - An error response if the sign-in or user retrieval fails.
     *
     * @param emailAuthCredential The credentials of the user, including email and password.
     * @return A [Flow] emitting a [AppResponse] containing:
     * - [SessionInfo] on successful sign-in,
     * - [AppResponse.Empty] if no user details are found,
     * - An error if the process fails (e.g., authentication failure or network error).
     *//*
    fun signIn(emailAuthCredential: EmailAuthCredential): Flow<AppResponse<SessionInfo>>

    */
    /**
     * Signs out the current authenticated user.
     *
     * This method performs the sign-out operation and does not return any value.
     * It effectively logs the user out of the system.
     *//*
    fun signOut()

    */
    /**


     */
    /**
     * Retrieves the logged-in user along with their associated person details.
     *
     * @return A [Flow] emitting:
     * - [AppResponse.Success] containing [SessionInfo] if successful, or an error response if the user is not logged in.
     * - [AppResponse.Error] if any error occurs.
     *//*
    fun getSessionInfo(): Flow<AppResponse<SessionInfo>>

    */
    /**
     * Creates a new system access based on the provided access requirements.
     *
     * This method processes the provided [NewAccessRequirements] and returns a flow
     * with a [AppResponse] indicating the success (Unit) or failure (error response) of the access creation.
     *
     * @param requirements The requirements for creating a new system access.
     * @return A [Flow] emitting the [AppResponse] containing [Unit] on success, or an error response if access creation fails.
     *//*
    fun createNewSystemAccess(requirements: NewAccessRequirements): Flow<AppResponse<Unit>>
*/
