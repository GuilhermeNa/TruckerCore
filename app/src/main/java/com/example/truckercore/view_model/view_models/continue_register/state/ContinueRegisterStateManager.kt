package com.example.truckercore.view_model.view_models.continue_register.state

import com.example.truckercore._shared.classes.Email
import com.example.truckercore.model.shared.errors.InvalidStateException
import com.example.truckercore.view_model._shared._base.managers.StateManager

class ContinueRegisterStateManager :
    StateManager<ContinueRegisterState>(initialState = ContinueRegisterState()) {

    fun setIdleState(direction: ContinueRegisterDirection) {
        val newState = getStateValue().idle(direction)
        setState(newState)
    }

    fun getDirection(): ContinueRegisterDirection {
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