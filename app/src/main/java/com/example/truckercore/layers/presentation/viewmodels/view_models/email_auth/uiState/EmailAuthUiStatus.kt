package com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.uiState

sealed class EmailAuthUiStatus {

    data object Idle : com.example.truckercore.presentation.viewmodels.view_models.email_auth.uiState.EmailAuthUiStatus()
    data object Creating : com.example.truckercore.presentation.viewmodels.view_models.email_auth.uiState.EmailAuthUiStatus()
    data object Created : com.example.truckercore.presentation.viewmodels.view_models.email_auth.uiState.EmailAuthUiStatus()

    fun isCreating() = this is com.example.truckercore.presentation.viewmodels.view_models.email_auth.uiState.EmailAuthUiStatus.Creating

}