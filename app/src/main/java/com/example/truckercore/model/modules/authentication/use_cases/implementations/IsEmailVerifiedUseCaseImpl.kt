package com.example.truckercore.model.modules.authentication.use_cases.implementations

import com.example.truckercore.model.infrastructure.integration.auth.for_app.repository.AuthenticationRepository
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.IsEmailVerifiedUseCase
import com.example.truckercore.model.shared.utils.sealeds.AppResult

class IsEmailVerifiedUseCaseImpl(
    private val authRepository: AuthenticationRepository
) : IsEmailVerifiedUseCase {

    override fun invoke(): AppResult<Boolean> =
        authRepository.isEmailVerified()

}