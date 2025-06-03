package com.example.truckercore.model.infrastructure.integration.auth.for_app.repository

import com.example.truckercore._shared.classes.AppResponse
import com.example.truckercore._shared.classes.AppResult
import com.example.truckercore._shared.classes.Email
import com.example.truckercore._shared.classes.Password
import com.example.truckercore.model.infrastructure.integration.auth.for_api.AuthSource
import com.example.truckercore.model.modules.authentication.data.UID

class AuthenticationRepositoryImpl(
    private val authSource: AuthSource,
    private val errorFactory: AuthRepositoryErrorFactory
) : AuthenticationRepository {

    override suspend fun createUserWithEmail(email: Email, password: Password): AppResult<Unit> =
        try {
            authSource.createUserWithEmail(email, password)
            AppResult.Success(Unit)
        } catch (e: Exception) {
            val appError = errorFactory.creatingUser(e)
            AppResult.Error(appError)
        }

    override suspend fun sendEmailVerification(): AppResult<Unit> = try {
        authSource.sendEmailVerification()
        AppResult.Success(Unit)
    } catch (e: Exception) {
        val appError = errorFactory.verifyingEmail(e)
        AppResult.Error(appError)
    }

    override suspend fun observeEmailValidation(): AppResult<Unit> = try {
        authSource.observeEmailValidation()
        AppResult.Success(Unit)
    } catch (e: Exception) {
        val appError = errorFactory.observingEmail(e)
        AppResult.Error(appError)
    }

    override suspend fun signIn(email: Email, password: Password): AppResult<Unit> = try {
        authSource.signInWithEmail(email, password)
        AppResult.Success(Unit)
    } catch (e: Exception) {
        val appError = errorFactory.signingIn(e)
        AppResult.Error(appError)
    }

    override fun signOut() {
        authSource.signOut()
    }

    override fun thereIsLoggedUser(): Boolean {
        return authSource.thereIsLoggedUser()
    }

    override fun getUserEmail(): AppResponse<Email> = try {
        authSource.getUserEmail()
            ?.let { AppResponse.Success(Email.from(it)) }
            ?: AppResponse.Empty
    } catch (e: Exception) {
        val appError = errorFactory.accessLoggedUser(e)
        AppResponse.Error(appError)
    }

    override fun isEmailVerified(): AppResult<Boolean> = try {
        val result = authSource.isEmailVerified()
        AppResult.Success(result)
    } catch (e: Exception) {
        val appError = errorFactory.accessLoggedUser(e)
        AppResult.Error(appError)
    }

    override suspend fun sendPasswordResetEmail(email: Email): AppResult<Unit> = try {
        authSource.sendPasswordResetEmail(email)
        AppResult.Success(Unit)
    } catch (e: Exception) {
        val appError = errorFactory.recoveringEmail(e)
        AppResult.Error(appError)
    }

    override fun getUid(): AppResult<UID> = try {
        val uid = authSource.getUid()
        AppResult.Success(UID(uid))
    } catch (e: Exception) {
        val appError = errorFactory.accessLoggedUser(e)
        AppResult.Error(appError)
    }

}