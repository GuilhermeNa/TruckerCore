package com.example.truckercore.model.modules.authentication.use_cases.interfaces

import com.example.truckercore.model.infrastructure.integration.auth.for_app.data.UserCategory
import com.example.truckercore._utils.classes.AppResult

interface UpdateUserProfileUseCase {

    suspend operator fun invoke(profile: UserCategory): AppResult<Unit>

}