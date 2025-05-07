package com.example.truckercore.model.modules.authentication.use_cases.interfaces

import com.example.truckercore.model.infrastructure.integration.auth.for_app.requirements.EmailCredential
import com.example.truckercore.model.shared.utils.sealeds.AppResult

interface SignUseCase {

    suspend fun signIn(credential: EmailCredential): AppResult<Unit>

    fun signOut()

}