package com.example.truckercore.model.infrastructure.integration.auth.for_app.repository

import com.example.truckercore._utils.classes.AppResponse
import com.example.truckercore._utils.classes.AppResult
import com.example.truckercore._utils.classes.Email
import com.example.truckercore._utils.classes.Password
import com.example.truckercore.model.infrastructure.integration.auth.for_api.AuthSource
import com.example.truckercore.model.infrastructure.integration.auth.for_app.data.UserCategory

class AuthenticationRepositoryImpl(
    private val authSource: AuthSource,
    private val errorFactory: AuthErrorFactory
) : AuthenticationRepository {

    companion object {
        private const val CREATE_USER_ERR_MSG = "Failed to create user with email and password."
        private const val SEND_EMAIL_VERIFICATION_ERR_MSG = "Failed to send email verification."
        private const val UPDATE_PROFILE_ERR_MSG = "Failed to update user profile."
        private const val OBSERVE_EMAIL_VALIDATION_ERR_MSG = "Failed to observe email validation."
        private const val SIGN_IN_ERR_MSG = "Failed to sign in with provided credentials."
        private const val RECOVER_EMAIL_ERR_MSG = "Failed to send recover email."
    }

    override suspend fun createUserWithEmail(
        email: Email, password: Password
    ): AppResult<Unit> = try {
        authSource.createUserWithEmail(email, password)
        AppResult.Success(Unit)
    } catch (e: Exception) {
        errorFactory(message = CREATE_USER_ERR_MSG, cause = e)
    }

    override suspend fun sendEmailVerification(): AppResult<Unit> = try {
        authSource.sendEmailVerification()
        AppResult.Success(Unit)
    } catch (e: Exception) {
        errorFactory(message = SEND_EMAIL_VERIFICATION_ERR_MSG, cause = e)
    }

    override suspend fun updateUserProfile(profile: UserCategory): AppResult<Unit> = try {
        authSource.updateUserProfile(profile)
        AppResult.Success(Unit)
    } catch (e: Exception) {
        errorFactory(message = UPDATE_PROFILE_ERR_MSG, cause = e)
    }

    override suspend fun observeEmailValidation(): AppResult<Unit> = try {
        authSource.observeEmailValidation()
        AppResult.Success(Unit)
    } catch (e: Exception) {
        errorFactory(message = OBSERVE_EMAIL_VALIDATION_ERR_MSG, cause = e)
    }

    override suspend fun signIn(email: Email, password: Password): AppResult<Unit> = try {
        authSource.signInWithEmail(email, password)
        AppResult.Success(Unit)
    } catch (e: Exception) {
        errorFactory(message = SIGN_IN_ERR_MSG, cause = e)
    }

    override fun signOut() {
        authSource.signOut()
    }

    override fun thereIsLoggedUser(): AppResult<Boolean> {
        return AppResult.Success(authSource.thereIsLoggedUser())
    }

    override fun getUserEmail(): AppResponse<Email> {
        return authSource.getUserEmail()?.let {
            AppResponse.Success(Email.from(it))
        } ?: AppResponse.Empty
    }

    override fun isEmailVerified(): AppResult<Boolean> {
        val isVerified = authSource.isEmailVerified()
        return AppResult.Success(isVerified)
    }

    override suspend fun sendPasswordResetEmail(email: Email): AppResult<Unit> = try {
        authSource.sendPasswordResetEmail(email)
        AppResult.Success(Unit)
    } catch (e: Exception) {
        errorFactory(message = RECOVER_EMAIL_ERR_MSG, cause = e)
    }

}