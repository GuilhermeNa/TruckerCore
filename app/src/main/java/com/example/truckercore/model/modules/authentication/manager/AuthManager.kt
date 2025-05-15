package com.example.truckercore.model.modules.authentication.manager

import com.example.truckercore.model.infrastructure.integration.auth.for_app.data.EmailCredential
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.ObserveEmailValidationUseCase
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.SendVerificationEmailUseCase
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.ThereIsLoggedUserUseCase
import com.example.truckercore._utils.classes.AppResult
import com.example.truckercore._utils.classes.Email
import com.example.truckercore._utils.classes.AppResponse
import com.example.truckercore.model.modules.authentication.data.UID

/**
 * Interface representing the authentication service.
 * Provides methods for authenticating user credentials, signing in, signing out,
 * retrieving the current authenticated user, and creating new system access.
 */
interface AuthManager {

    suspend fun createUserWithEmail(credential: EmailCredential): AppResult<Unit>

    /**
     * Invokes the use case to send a verification email to the current Firebase user.
     * @see SendVerificationEmailUseCase.invoke
     */
    suspend fun sendVerificationEmail(): AppResult<Unit>

    /**
     * Invokes the use case to observe the email validation.
     * @see ObserveEmailValidationUseCase.invoke
     */
    suspend fun observeEmailValidation(): AppResult<Unit>

    /**
     *  Checks if there is a currently logged-in user.
     * @see ThereIsLoggedUserUseCase.invoke
     */
    fun thereIsLoggedUser(): Boolean

    fun getUserEmail(): AppResponse<Email>

    fun isEmailVerified(): AppResult<Boolean>

    fun signOut()

    suspend fun signIn(credential: EmailCredential): AppResult<Unit>

    suspend fun resetPassword(email: Email): AppResult<Unit>

    fun getUID(): AppResult<UID>

}
