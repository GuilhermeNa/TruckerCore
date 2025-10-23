package com.example.truckercore.layers.domain.use_case.authentication

import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.repository.auth.AuthenticationRepository

interface IsEmailVerifiedUseCase {
    fun invoke(): DataOutcome<Boolean>
}

class IsEmailVerifiedUseCaseImp(private val authRepository: AuthenticationRepository) :
    IsEmailVerifiedUseCase {

    override fun invoke(): DataOutcome<Boolean> = authRepository.isEmailVerified()

}