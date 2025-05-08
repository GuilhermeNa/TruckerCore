package com.example.truckercore.model.infrastructure.integration.auth.for_app.repository

import com.example.truckercore.model.infrastructure.integration.auth.for_api.AuthSource
import com.example.truckercore.model.infrastructure.integration.auth.for_app.app_errors.AuthenticationAppErrorFactory
import com.example.truckercore.model.infrastructure.integration.auth.for_app.requirements.UserCategory
import com.example.truckercore.model.shared.utils.sealeds.AppResult
import com.example.truckercore._utils.classes.Email
import com.example.truckercore._utils.classes.Password

class AuthenticationRepositoryImpl(
    private val dataSource: AuthSource,
    private val appErrorFactory: AuthenticationAppErrorFactory
) : AuthenticationRepository {

    override suspend fun createUserWithEmail(email: Email, password: Password): AppResult<Unit> =
        try {
            dataSource.createUserWithEmail(email, password)
            AppResult.Success(Unit)
        } catch (e: Exception) {
            val result = appErrorFactory.creatingUserWithEmail(e)
            AppResult.Error(result)
        }

    override suspend fun sendEmailVerification(): AppResult<Unit> = try {
        dataSource.sendEmailVerification()
        AppResult.Success(Unit)
    } catch (e: Exception) {
        val result = appErrorFactory.sendingEmailVerification(e)
        AppResult.Error(result)
    }

    override suspend fun updateUserProfile(profile: UserCategory): AppResult<Unit> =
        try {
            dataSource.updateUserProfile(profile)
            AppResult.Success(Unit)
        } catch (e: Exception) {
            val result = appErrorFactory.updatingProfile(e)
            AppResult.Error(result)
        }

    override suspend fun observeEmailValidation(): AppResult<Unit> = try {
        dataSource.observeEmailValidation()
        AppResult.Success(Unit)
    } catch (e: Exception) {
        val result = appErrorFactory.observingEmailValidation(e)
        AppResult.Error(result)
    }

    override suspend fun signIn(email: Email, password: Password): AppResult<Unit> = try {
        dataSource.signInWithEmail(email, password)
        AppResult.Success(Unit)
    } catch (e: Exception) {
        val result = appErrorFactory.signingIn(e)
        AppResult.Error(result)
    }

    override fun signOut() {
        dataSource.signOut()
    }

    override fun thereIsLoggedUser(): AppResult<Boolean> {
        return AppResult.Success(dataSource.thereIsLoggedUser())
    }

    override fun getUserEmail(): AppResult<Email> {
        return dataSource.getUserEmail()?.let {
            AppResult.Success(Email.from(it))
        } ?: TODO()
    }

    override fun isEmailVerified(): AppResult<Boolean> {
        val isVerified = dataSource.isEmailVerified()
        return AppResult.Success(isVerified)
    }

}