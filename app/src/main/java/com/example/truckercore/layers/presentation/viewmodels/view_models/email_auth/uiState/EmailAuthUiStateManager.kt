package com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.uiState

import com.example.truckercore.core.classes.EmailCredential
import com.example.truckercore.domain._shared._base.managers.StateManager

class EmailAuthUiStateManager : StateManager<com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.uiState.EmailAuthUiState>(initialState = com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.uiState.EmailAuthUiState()) {

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

    fun setCreatedState() {
        val newState = getStateValue().created()
        setState(newState)
    }

}