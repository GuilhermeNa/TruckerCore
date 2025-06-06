package com.example.truckercore.view_model.view_models.email_auth.uiState

import com.example.truckercore._shared.classes.Email
import com.example.truckercore._shared.classes.Password
import com.example.truckercore.model.infrastructure.integration.auth.for_app.data.EmailCredential
import com.example.truckercore.view_model._shared.helpers.ViewError
import kotlinx.coroutines.flow.MutableStateFlow

class EmailAuthUiStateManager {

    private val _state = MutableStateFlow(EmailAuthUiState())
    private val value get() = _state.value
    val state get() = _state

    fun updateEmailText(email: String) {
        val newState = value.updateEmail(email)
        setState(newState)
    }

    fun updatePasswordText(password: String) {
        val newState = value.updatePassword(password)
        setState(newState)
    }

    fun updateConfirmationText(confirmation: String) {
        val newState = value.updateConfirmation(confirmation)
        setState(newState)
    }

    fun setCreatingState() {
        val newState = value.copy(status = EmailAuthUiState.Status.Creating)
        setState(newState)
    }

    fun setSuccessState() {
        val newState = value.copy(status = EmailAuthUiState.Status.Success)
        setState(newState)
    }

    fun setUiErrorState() {
        val newState = value.copy(status = EmailAuthUiState.Status.Error(ViewError.Critical))
        setState(newState)
    }

    private fun setState(newState: EmailAuthUiState) {
        _state.value = newState
    }

    fun getCredential() = EmailCredential(
        email = Email.from(value.emailField.text),
        password = Password.from(value.passwordField.text)
    )

    fun setDefaultState() {
        val newState = value.copy(status = EmailAuthUiState.Status.AwaitingInput.Ready)
        setState(newState)
    }

}