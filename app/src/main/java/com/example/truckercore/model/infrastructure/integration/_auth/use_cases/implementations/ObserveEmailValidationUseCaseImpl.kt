package com.example.truckercore.model.infrastructure.integration._auth.use_cases.implementations

import com.example.truckercore.model.infrastructure.integration._auth.repository.AuthenticationRepository
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.interfaces.ObserveEmailValidationUseCase
import com.example.truckercore.model.shared.utils.sealeds.AppResult

class ObserveEmailValidationUseCaseImpl(
    private val authenticationRepository: com.example.truckercore.model.infrastructure.integration._auth.repository.AuthenticationRepository
): ObserveEmailValidationUseCase {

    override suspend fun invoke(): AppResult<Unit> = authenticationRepository.observeEmailValidation()

}