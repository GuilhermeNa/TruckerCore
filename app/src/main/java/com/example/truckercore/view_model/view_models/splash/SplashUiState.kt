package com.example.truckercore.view_model.view_models.splash

sealed class SplashUiState {

    data object Initial : SplashUiState()

    data object FirstAccess : SplashUiState()

    sealed class UserLoggedIn : SplashUiState() {
        data object ProfileIncomplete : UserLoggedIn()
        data object SystemAccessAllowed : UserLoggedIn()
        data object SystemAccessDenied : UserLoggedIn()
    }

    data object UserNotFound : SplashUiState()

    data class Error(val error: Exception) : SplashUiState()

}