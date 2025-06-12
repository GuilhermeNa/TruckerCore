package com.example.truckercore.view_model.view_models.continue_register.use_case

import com.example.truckercore._shared.expressions.extractData
import com.example.truckercore._shared.expressions.getOrElse
import com.example.truckercore.model.modules.aggregation.system_access.use_cases.is_user_registered_in_system.IsUserRegisteredInSystemUseCase
import com.example.truckercore.model.modules.authentication.manager.AuthManager
import com.example.truckercore.view_model.view_models.continue_register.state.ContinueRegisterDirection

class ContinueRegisterViewUseCase(
    private val authManager: AuthManager,
    private val isUserRegistered: IsUserRegisteredInSystemUseCase
) {

    suspend operator fun invoke(
        verifyEmail: () -> ContinueRegisterDirection,
        userName: () -> ContinueRegisterDirection,
        complete: () -> ContinueRegisterDirection
    ) = when {
        !isVerified() -> verifyEmail()
        !userRegistered() -> userName()
        else -> complete()
    }

    private fun isVerified(): Boolean {
        return authManager.isEmailVerified().extractData()
    }

    private suspend fun userRegistered(): Boolean {
        val uid = authManager.getUID().extractData()
        return isUserRegistered(uid).getOrElse { return false }
    }

}