package com.example.truckercore.layers.domain.use_case.authentication

import com.example.truckercore.layers.data.base.outcome.DataOutcome

interface IsProfileCompleteUseCase {

    operator fun invoke(): DataOutcome<Boolean>

}

class IsProfileCompleteUseCaseImpl: IsProfileCompleteUseCase {

    override fun invoke(): DataOutcome<Boolean> {
        TODO("Not yet implemented")
    }

}