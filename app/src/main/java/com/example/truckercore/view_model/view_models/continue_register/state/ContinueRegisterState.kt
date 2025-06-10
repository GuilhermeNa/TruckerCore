package com.example.truckercore.view_model.view_models.continue_register.state

import com.example.truckercore.view_model._shared._contracts.State

sealed class ContinueRegisterState : State {

    data object Initializing : ContinueRegisterState()

    data object Idle : ContinueRegisterState()

}