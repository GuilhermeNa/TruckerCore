package com.example.truckercore.model.infrastructure.security.authentication.service

import com.example.truckercore.model.infrastructure.integration.auth.for_app.requirements.EmailCredential
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.NewEmailResult
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.interfaces.CreateUserAndVerifyEmailUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.interfaces.ObserveEmailValidationUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.interfaces.SendVerificationEmailUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.interfaces.ThereIsLoggedUserUseCase
import com.example.truckercore.model.shared.utils.sealeds.AppResult

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
    suspend fun createUserAndVerifyEmail(credential: EmailCredential): NewEmailResult

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

}
