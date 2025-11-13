package com.example.truckercore.layers.domain.use_case.authentication

import com.example.truckercore.core.my_lib.classes.EmailCredential
import com.example.truckercore.layers.data.base.outcome.OperationOutcome
import com.example.truckercore.layers.data.repository.auth.AuthenticationRepository

interface CreateUserWithEmailUseCase {

    suspend operator fun invoke(credential: EmailCredential): OperationOutcome

}

class CreateUserWithEmailUseCaseImpl(
    private val repository: AuthenticationRepository
) : CreateUserWithEmailUseCase {

    override suspend fun invoke(credential: EmailCredential): OperationOutcome =
        with(credential) {
            repository.createUserWithEmail(email, password)
        }

}


