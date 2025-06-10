package com.example.truckercore.view_model.view_models.continue_register.state

import com.example.truckercore.view_model._shared._base.managers.StateManager

class ContinueRegisterStateManager :
    StateManager<ContinueRegisterState>(initialState = ContinueRegisterState.Initializing) {

    fun setIdleState() {
        setState(ContinueRegisterState.Idle)
    }

}