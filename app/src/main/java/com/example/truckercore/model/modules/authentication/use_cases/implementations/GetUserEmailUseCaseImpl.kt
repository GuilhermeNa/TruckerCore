package com.example.truckercore.model.modules.authentication.use_cases.implementations

import com.example.truckercore.model.infrastructure.integration.auth.for_app.repository.AuthenticationRepository
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.GetUserEmailUseCase
import com.example.truckercore._shared.classes.Email
import com.example.truckercore._shared.classes.AppResponse

class GetUserEmailUseCaseImpl(
    private val authRepository: AuthenticationRepository
): GetUserEmailUseCase {

    override fun invoke(): AppResponse<Email> {
        return authRepository.getUserEmail()
    }


}
