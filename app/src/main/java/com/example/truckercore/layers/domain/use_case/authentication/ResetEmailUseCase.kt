package com.example.truckercore.layers.domain.use_case.authentication

import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.layers.data.base.outcome.OperationOutcome
import com.example.truckercore.layers.data.repository.auth.AuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface ResetEmailUseCase {
    suspend fun invoke(email: Email): OperationOutcome
}

class ResetEmailUseCaseImp(
    private val repository: AuthenticationRepository
) : ResetEmailUseCase {

    override suspend fun invoke(email: Email): OperationOutcome =
        withContext(Dispatchers.IO) { repository.sendPasswordResetEmail(email) }

}