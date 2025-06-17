package com.example.truckercore.view_model.view_models.splash.use_case

import com.example.truckercore._shared.expressions.extractData
import com.example.truckercore._shared.expressions.getClassName
import com.example.truckercore.model.infrastructure.integration.preferences.PreferencesRepository
import com.example.truckercore.model.logger.AppLogger
import com.example.truckercore.model.modules.aggregation.system_access.manager.SystemAccessManager
import com.example.truckercore.model.modules.authentication.manager.AuthManager
import com.example.truckercore.view_model._shared.helpers.ViewError
import com.example.truckercore.view_model._shared.helpers.ViewResult

class SplashViewUseCase(
    private val authService: AuthManager,
    private val preferences: PreferencesRepository,
    private val systemAccessManager: SystemAccessManager
) {

    suspend fun invoke(): ViewResult<SplashDirection> = try {
        val direction = when {
            isFirstAccess() -> SplashDirection.WELCOME
            userNotFound() -> SplashDirection.LOGIN
            registerIncomplete() -> SplashDirection.CONTINUE_REGISTER
            else -> SplashDirection.MAIN
        }

        ViewResult.Success(direction)
    } catch (e: Exception) {
        AppLogger.e(getClassName(), ERROR_MSG, e)
        ViewResult.Error(ViewError.Critical)
    }

    private suspend fun isFirstAccess(): Boolean {
        val isFirstAccess = preferences.isFirstAccess()
        val hasLoggedUser = authService.thereIsLoggedUser()

        if (isFirstAccess && hasLoggedUser) {
            authService.signOut()
        }

        return isFirstAccess
    }

    private fun userNotFound(): Boolean {
        return !authService.thereIsLoggedUser()
    }

    private suspend fun registerIncomplete(): Boolean {
        // Check if Email is Verified
        val isEmailVerified = authService.isEmailVerified().extractData()
        if (!isEmailVerified) return true

        // Check if user have been registered in system
        val uid = authService.getUID().extractData()
        return !systemAccessManager.isUserRegistered(uid).extractData()
    }

    private companion object {
        private const val ERROR_MSG =
            "Error while determining navigation direction in SplashViewUseCase."
    }

}