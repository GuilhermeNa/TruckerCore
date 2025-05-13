package com.example.truckercore.model.modules.authentication.use_cases.implementations

import com.example.truckercore.model.infrastructure.integration.auth.for_app.repository.AuthenticationRepository
import com.example.truckercore.model.infrastructure.integration.auth.for_app.data.EmailCredential
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.SignUseCase
import com.example.truckercore._utils.classes.AppResult

class SignUseCaseImpl(
    private val authRepository: AuthenticationRepository
) : SignUseCase {

    override suspend fun signIn(credential: EmailCredential): AppResult<Unit> =
        authRepository.signIn(credential.email, credential.password)

    override fun signOut() {
        authRepository.signOut()
    }

}