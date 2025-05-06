package com.example.truckercore.view_model.view_models.login

import com.example.truckercore.model.errors.AppException

sealed class LoginEffect {

    sealed class Authenticated: LoginEffect() {
        data object RegistrationCompleted : Authenticated()
        data object AwaitingRegistration: Authenticated()
    }

    data class Error(val error: AppException) : LoginEffect()

}