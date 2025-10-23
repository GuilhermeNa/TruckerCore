package com.example.truckercore.layers.domain.use_case.authentication

import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.repository.auth.AuthenticationRepository

interface IsUserLoggedUseCase {
    fun invoke(): DataOutcome<Boolean>
}

class IsUserLoggedUseCaseImp(private val authenticationRepository: AuthenticationRepository) :
    IsUserLoggedUseCase {

    override fun invoke(): DataOutcome<Boolean> = authenticationRepository.thereIsLoggedUser()

}