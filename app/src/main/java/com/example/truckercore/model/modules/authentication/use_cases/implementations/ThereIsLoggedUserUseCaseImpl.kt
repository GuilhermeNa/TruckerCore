package com.example.truckercore.model.modules.authentication.use_cases.implementations

import com.example.truckercore.model.infrastructure.integration.auth.for_app.repository.AuthenticationRepository
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.ThereIsLoggedUserUseCase
import com.example.truckercore._utils.classes.AppResult

class ThereIsLoggedUserUseCaseImpl(
    private val authenticationRepository: AuthenticationRepository
): ThereIsLoggedUserUseCase {

    override fun invoke(): AppResult<Boolean> = authenticationRepository.thereIsLoggedUser()

}