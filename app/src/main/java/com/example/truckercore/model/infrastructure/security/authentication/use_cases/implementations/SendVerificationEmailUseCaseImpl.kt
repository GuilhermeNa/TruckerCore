package com.example.truckercore.model.infrastructure.security.authentication.use_cases.implementations

import com.example.truckercore.model.infrastructure.security.authentication.repository.AuthenticationRepository
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.interfaces.SendVerificationEmailUseCase
import com.example.truckercore.model.shared.utils.sealeds.AppResult

internal class SendVerificationEmailUseCaseImpl(
    private val authRepository: AuthenticationRepository
) : SendVerificationEmailUseCase {

    override suspend fun invoke(): AppResult<Unit> = authRepository.sendEmailVerification()

}