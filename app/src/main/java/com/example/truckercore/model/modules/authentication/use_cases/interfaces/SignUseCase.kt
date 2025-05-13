package com.example.truckercore.model.modules.authentication.use_cases.interfaces

import com.example.truckercore.model.infrastructure.integration.auth.for_app.data.EmailCredential
import com.example.truckercore._utils.classes.AppResult

interface SignUseCase {

    suspend fun signIn(credential: EmailCredential): AppResult<Unit>

    fun signOut()

}