package com.example.truckercore.model.modules.authentication.use_cases.implementations

import com.example.truckercore.model.infrastructure.integration.auth.for_app.repository.AuthenticationRepository
import com.example.truckercore.model.infrastructure.integration.auth.for_app.data.EmailCredential
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.CreateUserWithEmailUseCase
import com.example.truckercore._utils.classes.AppResult

class CreateUserWithEmailUseCaseImpl(private val authRepository: AuthenticationRepository) :
    CreateUserWithEmailUseCase {

    override suspend fun invoke(credential: EmailCredential): AppResult<Unit> =
        authRepository.createUserWithEmail(credential.email, credential.password)

}