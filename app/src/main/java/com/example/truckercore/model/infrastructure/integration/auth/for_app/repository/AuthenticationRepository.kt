package com.example.truckercore.model.infrastructure.integration.auth.for_app.repository

import com.example.truckercore.model.infrastructure.integration.auth.for_app.requirements.UserProfile
import com.example.truckercore.model.shared.utils.sealeds.AppResult
import com.example.truckercore.model.shared.value_classes.Email
import com.example.truckercore.model.shared.value_classes.Password

interface AuthenticationRepository {

    suspend fun createUserWithEmail(email: Email, password: Password): AppResult<Unit>

    suspend fun sendEmailVerification(): AppResult<Unit>

    suspend fun updateUserProfile(profile: UserProfile): AppResult<Unit>

    suspend fun signIn(email: Email, password: Password): AppResult<Unit>

    suspend fun observeEmailValidation(): AppResult<Unit>

    fun signOut()

}