package com.example.truckercore.view_model.view_models.forget_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore._utils.classes.AppResult
import com.example.truckercore._utils.expressions.handleUiError
import com.example.truckercore._utils.expressions.launch
import com.example.truckercore._utils.expressions.mapAppResult
import com.example.truckercore.model.modules.authentication.manager.AuthManager
import com.example.truckercore.view.ui_error.UiErrorFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ForgetPasswordViewModel(private val authManager: AuthManager) : ViewModel() {

    private val stateManager = ForgetPasswordUiStateManager()
    val uiState get() = stateManager.uiState.asStateFlow()

    private val effectManager = ForgetPasswordEffectManager()
    val effect get() = effectManager.effect.asSharedFlow()

    fun onEvent(newEvent: ForgetPasswordEvent) {
        when (newEvent) {
            is ForgetPasswordEvent.UiEvent.EmailTextChange -> {
                stateManager.updateEmail(newEvent.text)
            }

            is ForgetPasswordEvent.UiEvent.SendButtonClicked -> {
                stateManager.setSendingEmailState()
                sendRecoverEmail()
            }

            is ForgetPasswordEvent.SystemEvent.EmailSent -> {
                stateManager.setSuccessState()
            }

            is ForgetPasswordEvent.SystemEvent.EmailFailed -> {
                newEvent.uiError.handleUiError(
                    onRecoverable = {
                        stateManager.setAwaitingInputState()
                        launch { effectManager.setRecoverableError(it) }
                    },
                    onCritical = { stateManager.setCriticalErrorState(it) }
                )
            }
        }
    }

    private fun sendRecoverEmail() {
        viewModelScope.launch {
            val email = stateManager.getEmail()
            val result = authManager.resetPassword(email)
            val newEvent = handleResult(result)
            delay(2000)
            onEvent(newEvent)
        }
    }

    private fun handleResult(result: AppResult<Unit>): ForgetPasswordEvent = result.mapAppResult(
        onSuccess = { ForgetPasswordEvent.SystemEvent.EmailSent },
        onError = { e ->
            val uiError = UiErrorFactory(e)
            ForgetPasswordEvent.SystemEvent.EmailFailed(uiError)
        }
    )

}