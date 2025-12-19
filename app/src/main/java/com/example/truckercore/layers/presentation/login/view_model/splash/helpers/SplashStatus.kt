package com.example.truckercore.layers.presentation.login.view_model.splash.helpers

sealed class SplashStatus {
    data object Initial : SplashStatus()
    data object Loading : SplashStatus()
    data object Loaded : SplashStatus()
}