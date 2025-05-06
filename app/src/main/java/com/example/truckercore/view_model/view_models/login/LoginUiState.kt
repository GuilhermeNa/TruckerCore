package com.example.truckercore.view_model.view_models.login

import com.example.truckercore._utils.classes.FieldState

sealed class LoginUiState {

    data object AwaitingInput : LoginUiState()

    data object ValidInput : LoginUiState()

    data class UiError(
        val password: FieldState,
        val email: FieldState
    ) : LoginUiState()

}