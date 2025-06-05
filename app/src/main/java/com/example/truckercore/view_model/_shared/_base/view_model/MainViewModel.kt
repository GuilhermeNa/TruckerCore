package com.example.truckercore.view_model._shared._base.view_model

import com.example.truckercore.model.modules.authentication.manager.AuthManager

class MainViewModel(private val authManager: AuthManager): LoggerViewModel() {

    fun hasLoggedUser(): Boolean {
        return authManager.thereIsLoggedUser()
    }

}