package com.example.truckercore.model.modules.authentication.use_cases.implementations

import com.example.truckercore.model.infrastructure.integration.auth.for_app.repository.AuthenticationRepository
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.GetUserEmailUseCase
import com.example.truckercore.model.shared.utils.sealeds.AppResult
import com.example.truckercore._utils.classes.Email

class GetUserEmailUseCaseImpl(
    private val authRepository: AuthenticationRepository
): GetUserEmailUseCase {

    override fun invoke(): AppResult<Email> {
        return authRepository.getUserEmail()
    }


}
