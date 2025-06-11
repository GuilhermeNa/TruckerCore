package com.example.truckercore.view_model.view_models.continue_register.state

import com.example.truckercore.view_model._shared._contracts.State

data class ContinueRegisterState(
    val direction: ContinueRegisterDirection? = null,
    val status: ContinueRegisterStatus = ContinueRegisterStatus.Initializing
) : State {

    fun idle(direction: ContinueRegisterDirection): ContinueRegisterState {
        return copy(direction = direction, status = ContinueRegisterStatus.Idle)
    }

}