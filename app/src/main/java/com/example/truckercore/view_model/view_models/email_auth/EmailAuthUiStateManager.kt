package com.example.truckercore.view_model.view_models.email_auth

import com.example.truckercore.view.ui_error.UiError
import kotlinx.coroutines.flow.MutableStateFlow

class EmailAuthUiStateManager {

    private val _state: MutableStateFlow<EmailAuthUiState> =
        MutableStateFlow(EmailAuthUiState.AwaitingInput.Default)
    val state get() = _state

    fun setAwaitingInputState(state: EmailAuthUiState.AwaitingInput) {

    }

    fun setCreatingState() {
        setState(EmailAuthUiState.Creating)
    }

    fun setSuccessState() {
        setState(EmailAuthUiState.Success)
    }

    fun setUiErrorState() {
        setState(EmailAuthUiState.Error(UiError.Critical()))
    }

    private fun setState(newState: EmailAuthUiState) {
        _state.value = newState
    }

}