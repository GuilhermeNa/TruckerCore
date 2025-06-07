package com.example.truckercore.view_model.view_models.email_auth.uiState

import com.example.truckercore.model.infrastructure.integration.auth.for_app.data.EmailCredential
import com.example.truckercore.view_model._shared._base.managers.StateManager

class EmailAuthUiStateManager : StateManager<EmailAuthUiState>(initialState = EmailAuthUiState()) {

    fun updateComponentsOnEmailChange(email: String) {
        val newState = getStateValue().updateEmail(email)
        setState(newState)
    }

    fun updateComponentsOnPasswordChange(password: String) {
        val newState = getStateValue().updatePassword(password)
        setState(newState)
    }

    fun updateComponentsOnConfirmationChange(confirmation: String) {
        val newState = getStateValue().updateConfirmation(confirmation)
        setState(newState)
    }

    fun setCreatingState() {
        val newState = getStateValue().creating()
        setState(newState)
    }

    fun setIdleState() {
        val newState = getStateValue().idle()
        setState(newState)
    }

    fun getCredential(): EmailCredential = getStateValue().getCredential()

}