package com.example.truckercore.view_model.view_models.login

import com.example.truckercore._utils.classes.FieldState

data class LoginUiState(
    val password: FieldState = FieldState(),
    val email: FieldState = FieldState(),
    val buttonEnabled: Boolean = false,
    val loading: Boolean = false
)