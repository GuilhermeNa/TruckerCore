package com.example.truckercore.model.modules.authentication.use_cases.implementations

import com.example.truckercore._shared.classes.AppResult
import com.example.truckercore.model.infrastructure.integration.auth.for_app.data.EmailCredential
import com.example.truckercore.model.infrastructure.integration.auth.for_app.repository.AuthenticationRepository
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.SignInUseCase

class SignInUseCaseImpl(
    private val authRepository: AuthenticationRepository
) : SignInUseCase {

    override suspend operator fun invoke(credential: EmailCredential): AppResult<Unit> =
        authRepository.signIn(credential.email, credential.password)

}