package com.example.truckercore.view_model.view_models.splash

import com.example.truckercore.model.errors.AppException

sealed class SplashEffect {

    data object FirstTimeAccess : SplashEffect()
    // Primeiro acesso -> navega p/ WelcomeFrag

    sealed class AlreadyAccessed : SplashEffect() {
        // Segundo acesso(+):

        data object RequireLogin : AlreadyAccessed()
        // !KeepLogged -> navega p/ Login

        sealed class AuthenticatedUser : AlreadyAccessed() {
            data object RegistrationCompleted : AuthenticatedUser()
            // Cadastro valido -> entra sistema

            data object AwaitingRegistration : AuthenticatedUser()
            // Cadastro pendente -> navega p/ ContinueFrag
        }
    }

    data class Error(val error: AppException): SplashEffect()

}