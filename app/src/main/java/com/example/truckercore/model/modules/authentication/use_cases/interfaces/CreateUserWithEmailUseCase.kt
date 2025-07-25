package com.example.truckercore.model.modules.authentication.use_cases.interfaces

import com.example.truckercore.model.infrastructure.integration.auth.for_app.data.EmailCredential
import com.example.truckercore._shared.classes.AppResult

interface CreateUserWithEmailUseCase {

    suspend fun invoke(credential: EmailCredential): AppResult<Unit>

}