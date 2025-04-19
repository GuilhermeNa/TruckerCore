package com.example.truckercore.model.infrastructure.integration._auth.repository

import com.example.truckercore.model.infrastructure.integration._auth.AuthSource
import com.example.truckercore.model.infrastructure.integration._auth.app_errors.AuthenticationAppErrorFactory
import com.example.truckercore.model.infrastructure.security.authentication.app_errors.AuthenticationAppErrorFactory
import com.example.truckercore.model.shared.utils.sealeds.AppResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

class AuthenticationRepositoryImpl(
    private val dataSource: AuthSource,
    private val appErrorFactory: AuthenticationAppErrorFactory
) : AuthenticationRepository {

    override suspend fun createUserWithEmail(
        email: String,
        password: String
    ): AppResult<FirebaseUser> = try {
        val result = dataSource.createUserWithEmail(email, password)
        AppResult.Success(result)
    } catch (e: Exception) {
        val result = appErrorFactory.handleCreateUserWithEmailError(e)
        AppResult.Error(result)
    }

    override suspend fun sendEmailVerification(): AppResult<Unit> = try {
        dataSource.sendEmailVerification()
        AppResult.Success(Unit)
    } catch (e: Exception) {
        val result = appErrorFactory.handleSendEmailVerificationError(e)
        AppResult.Error(result)
    }

    override suspend fun updateUserProfile(profile: UserProfileChangeRequest): AppResult<Unit> =
        try {
            dataSource.updateUserProfile(profile)
            AppResult.Success(Unit)
        } catch (e: Exception) {
            val result = appErrorFactory.handleUpdateUserNameError(e)
            AppResult.Error(result)
        }

    override suspend fun observeEmailValidation(): AppResult<Unit> = try {
        dataSource.observeEmailValidation()
        AppResult.Success(Unit)
    } catch (e: Exception) {
        val result = appErrorFactory.handleObservingEmailValidationError()
        AppResult.Error(result)
    }

    override suspend fun signIn(email: String, password: String): AppResult<Unit> = try {
        dataSource.signInWithEmail(email, password)
        AppResult.Success(Unit)
    } catch (e: Exception) {
        val result = appErrorFactory.handleSignInError(e)
        AppResult.Error(result)
    }

    override fun signOut() {
        dataSource.signOut()
    }

    override fun getCurrentUser(): FirebaseUser? = dataSource.getCurrentUser()

}