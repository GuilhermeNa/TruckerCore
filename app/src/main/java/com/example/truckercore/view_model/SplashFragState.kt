package com.example.truckercore.view_model

sealed class SplashFragState {

    data object Initial : SplashFragState()

    sealed class UserLoggedIn : SplashFragState() {
        data object ProfileIncomplete : UserLoggedIn()
        data object ProfileComplete : UserLoggedIn()
    }

    sealed class UserNotFound : SplashFragState() {
        data object FirstAccess : UserNotFound()
        data object NotFirstAccess : UserNotFound()
    }

    sealed class SystemAccess : SplashFragState() {
        data object AccessAllowed : SystemAccess()
        data object AccessDenied : SystemAccess()
    }

}