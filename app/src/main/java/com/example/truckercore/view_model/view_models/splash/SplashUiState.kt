package com.example.truckercore.view_model.view_models.splash

sealed class SplashUiState {

    data object Initial: SplashUiState()

    data object Loading: SplashUiState()

    data object Navigating: SplashUiState()

}