package com.example.truckercore.layers.domain.use_case.authentication

import com.example.truckercore.core.my_lib.classes.EmailCredential
import com.example.truckercore.layers.data.base.outcome.OperationOutcome
import com.example.truckercore.layers.data.repository.auth.AuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface CreateUserUseCase {
    suspend fun invoke(credential: EmailCredential): OperationOutcome
}

class CreateUserUseCaseImp(private val authRepository: AuthenticationRepository) :
    CreateUserUseCase {

    override suspend fun invoke(credential: EmailCredential): OperationOutcome =
        withContext(Dispatchers.IO) {
            authRepository.createUserWithEmail(credential.email, credential.password)
        }

}