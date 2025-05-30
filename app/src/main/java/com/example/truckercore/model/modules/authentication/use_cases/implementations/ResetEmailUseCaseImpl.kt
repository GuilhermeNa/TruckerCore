package com.example.truckercore.model.modules.authentication.use_cases.implementations

import com.example.truckercore._utils.classes.AppResult
import com.example.truckercore._utils.classes.Email
import com.example.truckercore.model.infrastructure.integration.auth.for_app.repository.AuthenticationRepository
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.ResetEmailUseCase

class ResetEmailUseCaseImpl(
    private val repository: AuthenticationRepository
) : ResetEmailUseCase {

    override suspend fun invoke(email: Email): AppResult<Unit> {
        return repository.sendPasswordResetEmail(email)
    }

}