package com.example.truckercore.layers.domain.use_case.authentication

import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.repository.auth.AuthenticationRepository

interface IsEmailVerifiedUseCase {
    operator fun invoke(): DataOutcome<Boolean>
}

class IsEmailVerifiedUseCaseImpl(
    private val authRepository: AuthenticationRepository
) : IsEmailVerifiedUseCase {

    override operator fun invoke(): DataOutcome<Boolean> = authRepository.isEmailVerified()

}