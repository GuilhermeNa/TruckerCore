package com.example.truckercore.layers.domain.use_case.authentication

import com.example.truckercore.layers.data.repository.auth.AuthenticationRepository

interface SignOutUseCase {
    fun invoke()
}

class SignOutUseCaseImpl(
    private val authRepository: AuthenticationRepository
) : SignOutUseCase {

    override fun invoke() {
        authRepository.signOut()
    }
}