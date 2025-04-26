package com.example.truckercore.model.modules.authentication.service

import com.example.truckercore.model.infrastructure.integration.auth.for_app.requirements.EmailCredential
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.CreateUserAndVerifyEmailUseCase
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.ObserveEmailValidationUseCase
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.SendVerificationEmailUseCase
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.ThereIsLoggedUserUseCase
import com.example.truckercore.model.shared.utils.sealeds.AppResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class AuthServiceImpl(
    private val createAndVerifyEmail: CreateUserAndVerifyEmailUseCase,
    private val sendVerificationEmail: SendVerificationEmailUseCase,
    private val observeEmailValidation: ObserveEmailValidationUseCase,
    private val thereIsLoggedUser: ThereIsLoggedUserUseCase
) : AuthService {

    override suspend fun createUserAndVerifyEmail(credential: EmailCredential) =
        withContext(Dispatchers.IO) { createAndVerifyEmail.invoke(credential) }

    override suspend fun sendVerificationEmail(): AppResult<Unit> =
        withContext(Dispatchers.IO) { sendVerificationEmail.invoke() }

    override suspend fun observeEmailValidation(): AppResult<Unit> =
        withContext(Dispatchers.IO) { observeEmailValidation.invoke() }

    override fun thereIsLoggedUser(): Boolean = thereIsLoggedUser.invoke()

}