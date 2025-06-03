package com.example.truckercore.view_model.view_models.login.state

import com.example.truckercore.view_model._shared._base.managers.StateManager
import com.example.truckercore.model.infrastructure.integration.auth.for_app.data.EmailCredential

class LoginStateManager : StateManager<LoginUiState>(initialState = LoginUiState()) {

    fun updateComponentsOnEmailChange(email: String) {
        val newState = getStateValue().updateEmail(email)
        setState(newState)
    }

    fun updateComponentsOnPasswordChange(password: String) {
        val newState = getStateValue().updatePassword(password)
        setState(newState)
    }

    fun setIdleState() {
        val newState = getStateValue().idle()
        setState(newState)
    }

    fun setLoadingState() {
        val newState = getStateValue().loading()
        setState(newState)
    }

    fun getCredential(): EmailCredential {
        return getStateValue().getCredential()
    }

    fun updateComponentsOnCheckBoxChange(isChecked: Boolean) {
        val newState = getStateValue().updateCheckBox(isChecked)
        setState(newState)
    }

    fun getCheckBoxState(): Boolean {
        return getStateValue().getCheckBoxState()
    }

}