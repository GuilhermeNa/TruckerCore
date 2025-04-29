package com.example.truckercore.model.modules.authentication.use_cases.interfaces

import com.example.truckercore.model.infrastructure.integration.auth.for_app.requirements.UserProfile
import com.example.truckercore.model.shared.utils.sealeds.AppResult

interface UpdateUserProfileUseCase {

    suspend operator fun invoke(profile: UserProfile): AppResult<Unit>

}