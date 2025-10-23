package com.example.truckercore.layers.presentation.viewmodels._shared._base.view_model

import com.example.truckercore.data.modules.authentication.manager.AuthManager

class MainViewModel(private val authManager: AuthManager): com.example.truckercore.presentation.viewmodels._shared._base.view_model.LoggerViewModel() {

    fun hasLoggedUser(): Boolean {
        return authManager.thereIsLoggedUser()
    }

}