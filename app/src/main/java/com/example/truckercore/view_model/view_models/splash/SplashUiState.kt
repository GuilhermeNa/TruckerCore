package com.example.truckercore.view_model.view_models.splash

import com.example.truckercore._utils.classes.contracts.UiState
import com.example.truckercore.view.ui_error.UiError

sealed class SplashUiState : UiState {

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