package com.example.truckercore.model.infrastructure.integration._auth.use_cases.implementations

import com.example.truckercore.model.infrastructure.integration._auth.repository.AuthenticationRepository
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.interfaces.ThereIsLoggedUserUseCase

class ThereIsLoggedUserUseCaseImpl(
    private val authenticationRepository: com.example.truckercore.model.infrastructure.integration._auth.repository.AuthenticationRepository
): ThereIsLoggedUserUseCase {

    override fun invoke(): Boolean = authenticationRepository.getCurrentUser() != null

}