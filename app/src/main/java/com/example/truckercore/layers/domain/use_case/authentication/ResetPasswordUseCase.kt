package com.example.truckercore.layers.domain.use_case.authentication

import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.layers.data.base.outcome.OperationOutcome
import com.example.truckercore.layers.data.repository.auth.AuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface ResetPasswordUseCase {
    suspend operator fun invoke(email: Email): OperationOutcome
}

class ResetPasswordUseCaseImpl(
    private val repository: AuthenticationRepository
) : ResetPasswordUseCase {

    override suspend operator fun invoke(email: Email): OperationOutcome =
        withContext(Dispatchers.IO) { repository.sendPasswordResetEmail(email) }

}