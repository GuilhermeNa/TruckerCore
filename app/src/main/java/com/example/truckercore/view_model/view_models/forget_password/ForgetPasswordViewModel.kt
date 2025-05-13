package com.example.truckercore.view_model.view_models.forget_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore._utils.classes.AppResult
import com.example.truckercore._utils.expressions.handleUiError
import com.example.truckercore._utils.expressions.mapAppResult
import com.example.truckercore.model.modules.authentication.manager.AuthManager
import com.example.truckercore.view.ui_error.UiError
import com.example.truckercore.view.ui_error.UiErrorFactory
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ForgetPasswordViewModel(
    private val authManager: AuthManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ForgetPasswordUiState())
    val uiState get() = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<ForgetPasswordEffect>()
    val effect get() = _effect.asSharedFlow()

    fun onEvent(newEvent: ForgetPasswordEvent) {
        when (newEvent) {
            is ForgetPasswordEvent.UiEvent.EmailTextChange -> {

            }

            is ForgetPasswordEvent.UiEvent.SendButtonClicked -> {
                setState(ForgetPasswordUiState.Status.SendingEmail)
                sendRecoverEmail()
            }

            is ForgetPasswordEvent.SystemEvent.EmailSent -> {
                setState(ForgetPasswordUiState.Status.Success)
            }

            is ForgetPasswordEvent.SystemEvent.EmailFailed -> {
                newEvent.uiError.handleUiError(
                    onRecoverable = { setEffect(it) },
                    onCritical = { setState(ForgetPasswordUiState.Status.Error(it)) }
                )

            }

        }
    }

    private fun setState(newState: ForgetPasswordUiState.Status) {
        val copy = _uiState.value.copy(status = newState.status)
        _uiState.value = copy
    }

    private fun setEffect(uiError: UiError.Recoverable) {
        viewModelScope.launch {
            _effect.emit(ForgetPasswordEffect.Error(uiError.message))
        }
    }

    private fun sendRecoverEmail() {
        viewModelScope.launch {
            val email = uiState.value.getEmail()
            val result = authManager.resetPassword(email)
            val newEvent = handleResult(result)
            onEvent(newEvent)
        }
    }

    private fun handleResult(result: AppResult<Unit>): ForgetPasswordEvent =
        result.mapAppResult(
            onSuccess = { ForgetPasswordEvent.SystemEvent.EmailSent },
            onError = { e ->
                val uiError = UiErrorFactory(e)
                ForgetPasswordEvent.SystemEvent.EmailFailed(uiError)
            }
        )

}