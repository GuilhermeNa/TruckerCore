package com.example.truckercore.layers.domain.use_case.authentication

import com.example.truckercore.core.my_lib.classes.EmailCredential
import com.example.truckercore.layers.data.base.outcome.OperationOutcome
import com.example.truckercore.layers.data.repository.auth.AuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface SignInUseCase {
    suspend operator fun invoke(credential: EmailCredential): OperationOutcome
}

class SignInUseCaseImpl(private val authRepository: AuthenticationRepository) : SignInUseCase {

    override suspend operator fun invoke(credential: EmailCredential): OperationOutcome =
        withContext(Dispatchers.IO) { authRepository.signIn(credential.email, credential.password) }

}