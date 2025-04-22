package com.example.truckercore.model.infrastructure.security.authentication.use_cases.implementations

import com.example.truckercore.model.infrastructure.integration.auth.for_app.repository.AuthenticationRepository
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.interfaces.ThereIsLoggedUserUseCase

class ThereIsLoggedUserUseCaseImpl(
    private val authenticationRepository: AuthenticationRepository
): ThereIsLoggedUserUseCase {

    override fun invoke(): Boolean = authenticationRepository.getCurrentUser() != null

}