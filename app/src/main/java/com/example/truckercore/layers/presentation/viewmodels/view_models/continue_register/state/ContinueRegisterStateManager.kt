package com.example.truckercore.layers.presentation.viewmodels.view_models.continue_register.state

import com.example.truckercore.core.classes.Email
import com.example.truckercore.data.shared.errors.InvalidStateException
import com.example.truckercore.domain._shared._base.managers.StateManager

class ContinueRegisterStateManager :
    StateManager<ContinueRegisterState>(initialState = ContinueRegisterState()) {

    fun setIdleState(direction: com.example.truckercore.presentation.viewmodels.view_models.continue_register.state.ContinueRegisterDirection) {
        val newState = getStateValue().idle(direction)
        setState(newState)
    }

    fun getDirection(): com.example.truckercore.presentation.viewmodels.view_models.continue_register.state.ContinueRegisterDirection {
        return getStateValue().direction ?: throw InvalidStateException(INVALID_DIRECTION_LOG_MSG)
    }

    fun updateEmailComponent(email: Email) {
        val newState = getStateValue().updateEmail(email)
        setState(newState)
    }

    companion object {
        private const val INVALID_DIRECTION_LOG_MSG =
            "Continue register direction is not initialized."
    }

}