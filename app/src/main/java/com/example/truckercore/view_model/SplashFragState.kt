package com.example.truckercore.view_model

sealed class SplashFragState {

    data object Initial : SplashFragState()

    data object FirstAccess : SplashFragState()

    sealed class UserLoggedIn : SplashFragState() {
        data object ProfileIncomplete : UserLoggedIn()
        data object SystemAccessAllowed : UserLoggedIn()
        data object SystemAccessDenied : UserLoggedIn()
    }

    data object UserNotFound : SplashFragState()

    data class Error(val error: Exception) : SplashFragState()

}