package com.example.truckercore.layers.domain.use_case.authentication

import com.example.truckercore.layers.data.base.outcome.OperationOutcome
import com.example.truckercore.layers.data.repository.auth.AuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface ObserveEmailValidationUseCase {
    suspend fun invoke(): OperationOutcome
}

class ObserveEmailValidationUseCaseImp(private val authenticationRepository: AuthenticationRepository) :
    ObserveEmailValidationUseCase {

    override suspend fun invoke(): OperationOutcome =
        withContext(Dispatchers.IO) { authenticationRepository.observeEmailValidation() }

}