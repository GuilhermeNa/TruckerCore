package com.example.truckercore.layers.domain.use_case.authentication

import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.repository.auth.AuthenticationRepository

interface GetUserEmailUseCase {

    operator fun invoke(): DataOutcome<Email>

}

class GetUserEmailUseCaseImpl(
    private val authRepository: AuthenticationRepository
) : GetUserEmailUseCase {

    override operator fun invoke(): DataOutcome<Email> = authRepository.getUserEmail()

}
