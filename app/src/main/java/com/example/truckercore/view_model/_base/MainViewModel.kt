package com.example.truckercore.view_model._base

import com.example.truckercore.model.modules.authentication.manager.AuthManager

class MainViewModel(
    private val authManager: AuthManager
): LoggerViewModel() {

    fun hasLoggedUser(): Boolean {
        return authManager.thereIsLoggedUser()
    }

}