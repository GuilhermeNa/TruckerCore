package com.example.truckercore.model.infrastructure.security.authentication.use_cases.implementations

import com.example.truckercore.model.infrastructure.security.authentication.repository.AuthenticationRepository
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.interfaces.ObserveEmailValidationUseCase
import com.example.truckercore.model.shared.utils.sealeds.AppResult
import kotlinx.coroutines.flow.Flow

class ObserveEmailValidationUseCaseImpl(
    private val authenticationRepository: AuthenticationRepository
): ObserveEmailValidationUseCase {

    override suspend fun invoke(): AppResult<Unit> = authenticationRepository.observeEmailValidation()

}