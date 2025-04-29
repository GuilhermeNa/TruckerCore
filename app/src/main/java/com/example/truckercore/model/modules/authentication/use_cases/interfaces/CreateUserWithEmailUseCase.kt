package com.example.truckercore.model.modules.authentication.use_cases.interfaces

import com.example.truckercore.model.infrastructure.integration.auth.for_app.requirements.EmailCredential
import com.example.truckercore.model.shared.utils.sealeds.AppResult

interface CreateUserWithEmailUseCase {

    suspend fun invoke(credential: EmailCredential): AppResult<Unit>

}