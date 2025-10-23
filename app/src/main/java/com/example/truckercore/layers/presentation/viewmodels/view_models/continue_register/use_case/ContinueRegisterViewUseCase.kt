package com.example.truckercore.layers.presentation.viewmodels.view_models.continue_register.use_case

import com.example.truckercore.core.expressions.extractData
import com.example.truckercore.core.expressions.getOrElse
import com.example.truckercore.data.modules.aggregation.system_access.use_cases.is_user_registered_in_system.IsUserRegisteredInSystemUseCase
import com.example.truckercore.data.modules.authentication.manager.AuthManager
import com.example.truckercore.presentation.viewmodels.view_models.continue_register.state.ContinueRegisterDirection

class ContinueRegisterViewUseCase(
    private val authManager: AuthManager,
    private val isUserRegistered: IsUserRegisteredInSystemUseCase
) {

    suspend operator fun invoke(
        verifyEmail: () -> com.example.truckercore.presentation.viewmodels.view_models.continue_register.state.ContinueRegisterDirection,
        userName: () -> com.example.truckercore.presentation.viewmodels.view_models.continue_register.state.ContinueRegisterDirection,
        complete: () -> com.example.truckercore.presentation.viewmodels.view_models.continue_register.state.ContinueRegisterDirection
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