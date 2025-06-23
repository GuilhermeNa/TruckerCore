package com.example.truckercore.view_model.view_models.email_auth.uiState

sealed class EmailAuthUiStatus {

    data object Idle : EmailAuthUiStatus()
    data object Creating : EmailAuthUiStatus()
    data object Created : EmailAuthUiStatus()

    fun isCreating() = this is Creating

}