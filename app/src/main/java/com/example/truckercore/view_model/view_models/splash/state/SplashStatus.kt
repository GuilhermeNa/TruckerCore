package com.example.truckercore.view_model.view_models.splash.state

import com.example.truckercore.view_model._shared.helpers.ViewError

sealed class SplashStatus {

    data object Initial : SplashStatus()

    data object Loading : SplashStatus()

    sealed class Navigating : SplashStatus() {
        data object Welcome: Navigating()
        data object ContinueRegister: Navigating()
        data object Login: Navigating()
        data object PreparingAmbient: Navigating()
    }

    data class Error(val uiError: ViewError) : SplashStatus()

}