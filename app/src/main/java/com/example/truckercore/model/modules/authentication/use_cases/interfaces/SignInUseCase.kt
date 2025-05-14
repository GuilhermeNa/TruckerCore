package com.example.truckercore.model.modules.authentication.use_cases.interfaces

import com.example.truckercore._utils.classes.AppResult
import com.example.truckercore.model.infrastructure.integration.auth.for_app.data.EmailCredential

interface SignInUseCase {

    suspend operator fun invoke(credential: EmailCredential): AppResult<Unit>

}