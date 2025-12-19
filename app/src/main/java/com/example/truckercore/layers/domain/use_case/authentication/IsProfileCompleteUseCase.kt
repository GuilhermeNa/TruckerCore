package com.example.truckercore.layers.domain.use_case.authentication

import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.repository.auth.AuthenticationRepository

interface IsProfileCompleteUseCase {
    operator fun invoke(): DataOutcome<Boolean>
}

class IsProfileCompleteUseCaseImpl(
    private val repository: AuthenticationRepository
) : IsProfileCompleteUseCase {

    override fun invoke(): DataOutcome<Boolean> {
        TODO("Not yet implemented")
    }

}