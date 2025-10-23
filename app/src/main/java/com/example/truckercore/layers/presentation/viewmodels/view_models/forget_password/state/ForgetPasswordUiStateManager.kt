package com.example.truckercore.layers.presentation.viewmodels.view_models.forget_password.state

import com.example.truckercore.core.classes.Email
import com.example.truckercore.domain._shared._base.managers.StateManager

class ForgetPasswordUiStateManager :
    StateManager<ForgetPasswordUiState>(initialState = ForgetPasswordUiState()) {

    fun updateComponentsOnEmailChange(email: String) {
        val updateState = getStateValue().updateEmail(email)
        setState(updateState)
    }

    fun setLoadingState() {
        val updatedState = getStateValue().loading()
        setState(updatedState)
    }

    fun setIdleState() {
        val updatedState = getStateValue().idle()
        setState(updatedState)
    }

    fun getEmail(): Email {
        val emailString = getStateValue().passwordComponent.text
        return Email.from(emailString)
    }

}
