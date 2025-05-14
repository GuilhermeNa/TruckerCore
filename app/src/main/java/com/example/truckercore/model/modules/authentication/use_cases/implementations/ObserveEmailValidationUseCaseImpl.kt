package com.example.truckercore.model.modules.authentication.use_cases.implementations

import com.example.truckercore._utils.classes.AppResult
import com.example.truckercore.model.infrastructure.integration.auth.for_app.repository.AuthenticationRepository
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.ObserveEmailValidationUseCase

class ObserveEmailValidationUseCaseImpl(
    private val authenticationRepository: AuthenticationRepository
) : ObserveEmailValidationUseCase {

    override suspend fun invoke(): AppResult<Unit> =
        authenticationRepository.observeEmailValidation()

}