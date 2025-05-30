package com.example.truckercore.view_model.view_models.forget_password.state

import com.example.truckercore._utils.classes.Email
import com.example.truckercore._utils.classes.abstractions.StateManager

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
