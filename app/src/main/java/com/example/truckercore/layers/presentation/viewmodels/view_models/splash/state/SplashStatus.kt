package com.example.truckercore.layers.presentation.viewmodels.view_models.splash.state

sealed class SplashStatus {
    data object Initial : SplashStatus()
    data object Loading : SplashStatus()
    data object Loaded : SplashStatus()
}