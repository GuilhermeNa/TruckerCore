package com.example.truckercore.view_model.view_models.splash

import com.example.truckercore.view_model._shared._contracts.State
import com.example.truckercore.view._shared.ui_error.UiError

sealed class SplashUiState : State {

    data object Initial : SplashUiState()

    data object Loading : SplashUiState()

    sealed class Navigating : SplashUiState() {
        data object Welcome: Navigating()
        data object ContinueRegister: Navigating()
        data object Login: Navigating()
        data object PreparingAmbient: Navigating()
    }

    data class Error(val uiError: UiError) : SplashUiState()

}