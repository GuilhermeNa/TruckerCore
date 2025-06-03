package com.example.truckercore.model.modules.authentication.use_cases.implementations

import com.example.truckercore._shared.classes.AppResult
import com.example.truckercore.model.infrastructure.integration.auth.for_app.repository.AuthenticationRepository
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.IsEmailVerifiedUseCase

class IsEmailVerifiedUseCaseImpl(
    private val authRepository: AuthenticationRepository
) : IsEmailVerifiedUseCase {

    override fun invoke(): AppResult<Boolean> = authRepository.isEmailVerified()

}