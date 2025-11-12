package com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.state

sealed class EmailAuthUiStatus {

    data object Idle : EmailAuthUiStatus()
    data object Creating : EmailAuthUiStatus()
    data object Created : EmailAuthUiStatus()

    fun isCreating() = this is Creating

}