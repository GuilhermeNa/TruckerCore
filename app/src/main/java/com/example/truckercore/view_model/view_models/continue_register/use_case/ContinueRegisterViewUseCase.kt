package com.example.truckercore.view_model.view_models.continue_register.use_case

import com.example.truckercore.model.modules.authentication.manager.AuthManager
import com.example.truckercore.model.modules.user.manager.UserManager
import com.example.truckercore.view_model._shared.helpers.ViewResult

class ContinueRegisterViewUseCase(
    private val authManager: AuthManager,
    //private val userManager: UserManager
) {

    operator fun invoke(): ViewResult {
        TODO()
    }

}