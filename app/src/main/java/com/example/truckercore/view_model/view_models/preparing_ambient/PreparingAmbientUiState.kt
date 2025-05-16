package com.example.truckercore.view_model.view_models.preparing_ambient

sealed class PreparingAmbientUiState {

    data object LoadingSession : PreparingAmbientUiState()

    data object Success : PreparingAmbientUiState()

    data object Error : PreparingAmbientUiState()

}