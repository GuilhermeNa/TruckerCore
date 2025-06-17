package com.example.truckercore.view_model.view_models.splash.state

sealed class SplashStatus {
    data object Initial : SplashStatus()
    data object Loading : SplashStatus()
    data object Loaded : SplashStatus()
}