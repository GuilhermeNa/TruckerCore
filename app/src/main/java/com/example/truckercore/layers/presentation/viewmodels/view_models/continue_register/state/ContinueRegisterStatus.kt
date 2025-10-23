package com.example.truckercore.layers.presentation.viewmodels.view_models.continue_register.state

sealed class ContinueRegisterStatus {
    data object Initializing : ContinueRegisterStatus()
    data object Idle : ContinueRegisterStatus()

    fun isIdle() = this is Idle

}