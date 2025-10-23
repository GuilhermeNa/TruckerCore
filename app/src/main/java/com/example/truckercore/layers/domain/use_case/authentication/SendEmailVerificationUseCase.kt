package com.example.truckercore.layers.domain.use_case.authentication

import com.example.truckercore.layers.data.base.outcome.OperationOutcome
import com.example.truckercore.layers.data.repository.auth.AuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface SendEmailVerificationUseCase {
    suspend fun invoke(): OperationOutcome
}

internal class SendEmailVerificationUseCaseImp(
    private val authRepository: AuthenticationRepository
) : SendEmailVerificationUseCase {

    override suspend fun invoke(): OperationOutcome =
        withContext(Dispatchers.IO) { authRepository.sendEmailVerification() }

}