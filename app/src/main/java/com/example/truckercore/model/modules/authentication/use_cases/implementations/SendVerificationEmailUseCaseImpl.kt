package com.example.truckercore.model.modules.authentication.use_cases.implementations

import com.example.truckercore._shared.classes.AppResult
import com.example.truckercore.model.infrastructure.integration.auth.for_app.repository.AuthenticationRepository
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.SendVerificationEmailUseCase

internal class SendVerificationEmailUseCaseImpl(
    private val authRepository: AuthenticationRepository
) : SendVerificationEmailUseCase {

    override suspend fun invoke(): AppResult<Unit> = authRepository.sendEmailVerification()

}