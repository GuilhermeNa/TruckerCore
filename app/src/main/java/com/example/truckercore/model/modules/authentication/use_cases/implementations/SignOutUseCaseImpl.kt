package com.example.truckercore.model.modules.authentication.use_cases.implementations

import com.example.truckercore.model.infrastructure.integration.auth.for_app.repository.AuthenticationRepository
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.SignOutUseCase

class SignOutUseCaseImpl(
    private val authRepository: AuthenticationRepository
) : SignOutUseCase {

    override fun invoke() {
        authRepository.signOut()
    }

}