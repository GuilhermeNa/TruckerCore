package com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.state

sealed class EmailAuthUiStatus {

    data object WaitingInput : EmailAuthUiStatus()
    data object ReadyToCreate: EmailAuthUiStatus()
    data object Creating : EmailAuthUiStatus()

    fun isCreating() = this is Creating

}