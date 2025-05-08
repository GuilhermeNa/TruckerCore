package com.example.truckercore.model.modules.authentication.use_cases.implementations

import com.example.truckercore.model.infrastructure.integration.auth.for_app.repository.AuthenticationRepository
import com.example.truckercore.model.infrastructure.integration.auth.for_app.requirements.UserCategory
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.UpdateUserProfileUseCase
import com.example.truckercore.model.shared.utils.sealeds.AppResult

class UpdateUserNameUseCaseImpl(
    private val authRepository: AuthenticationRepository
) : UpdateUserProfileUseCase {

    override suspend fun invoke(profile: UserCategory): AppResult<Unit> =
        authRepository.updateUserProfile(profile)

}