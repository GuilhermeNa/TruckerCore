package com.example.truckercore.layers.presentation.viewmodels.view_models.login.state

import com.example.truckercore.domain._shared._base.managers.StateManager
import com.example.truckercore.core.classes.EmailCredential

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