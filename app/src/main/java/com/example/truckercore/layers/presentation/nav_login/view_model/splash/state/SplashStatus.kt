package com.example.truckercore.layers.presentation.nav_login.view_model.splash.state

sealed class SplashStatus {
    data object Initial : SplashStatus()
    data object Loading : SplashStatus()
    data object Loaded : SplashStatus()
}