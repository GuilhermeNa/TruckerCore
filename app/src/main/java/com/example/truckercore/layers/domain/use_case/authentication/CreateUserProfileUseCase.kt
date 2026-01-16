package com.example.truckercore.layers.domain.use_case.authentication

import com.example.truckercore.core.my_lib.classes.Name
import com.example.truckercore.layers.data.base.outcome.OperationOutcome
import com.example.truckercore.layers.data.repository.auth.AuthenticationRepository

interface CreateUserProfileUseCase {

    suspend operator fun invoke(name: Name): OperationOutcome

}

class CreateUserProfileUseCaseImpl(
    private val repository: AuthenticationRepository
): CreateUserProfileUseCase {

    override suspend operator fun invoke(name: Name): OperationOutcome =
        repository.updateName(name)

}