package com.example.truckercore.view_model.view_models.email_auth

import androidx.lifecycle.ViewModel
import com.example.truckercore._utils.expressions.handleAppResult
import com.example.truckercore._utils.expressions.launch
import com.example.truckercore._utils.expressions.logEvent
import com.example.truckercore.model.infrastructure.integration.preferences.PreferencesRepository
import com.example.truckercore.model.modules.authentication.manager.AuthManager
import com.example.truckercore.view_model.view_models.email_auth.effect.EmailAuthEffectManager
import com.example.truckercore.view_model.view_models.email_auth.event.EmailAuthEvent
import com.example.truckercore.view_model.view_models.email_auth.uiState.EmailAuthUiStateManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

private typealias UiEvent = EmailAuthEvent.UiEvent
private typealias SystemEvent = EmailAuthEvent.SystemEvent

class EmailAuthViewModel(
    private val preferences: PreferencesRepository,
    private val authService: AuthManager
) : ViewModel() {

    // Gerenciador do Estado da UI
    private val stateManager = EmailAuthUiStateManager()
    val state get() = stateManager.state.asStateFlow()

    // Gerenciador dos Efeitos da UI
    private val effectManager = EmailAuthEffectManager()
    val effect get() = effectManager.effect.asSharedFlow()

    //----------------------------------------------------------------------------------------------
    fun onEvent(event: EmailAuthEvent) = launch {
        logEvent(this@EmailAuthViewModel, event)
        when (event) {
            is UiEvent -> handleUiEvent(event)
            is SystemEvent -> handleSystemEvent(event)
        }
    }

    private suspend fun handleUiEvent(uiEvent: EmailAuthEvent.UiEvent) {
        when(uiEvent) {
            is EmailAuthEvent.UiEvent.Typing.EmailTextChange -> {
                stateManager.updateEmailText(uiEvent.text)
            }
            is EmailAuthEvent.UiEvent.Typing.PasswordTextChange -> {
                stateManager.updatePasswordText(uiEvent.text)
            }
            is EmailAuthEvent.UiEvent.Typing.ConfirmationTextChange -> {
                stateManager.updateConfirmationText(uiEvent.text)
            }
            EmailAuthEvent.UiEvent.Click.Background -> {
                effectManager.setClearFocusAndHideKeyboardEffect()
            }
            EmailAuthEvent.UiEvent.Click.ButtonAlreadyHaveAccount -> {
                effectManager.setNavigateToLoginEffect()
            }
            is EmailAuthEvent.UiEvent.Click.ButtonCreate -> {
                stateManager.setCreatingState()
                tryToAuthenticate()
            }
        }
    }

    private fun handleSystemEvent(systemEvent: EmailAuthEvent.SystemEvent) {
        when(systemEvent) {
            EmailAuthEvent.SystemEvent.Failure.ApiResultError -> {}
            is EmailAuthEvent.SystemEvent.Failure.InputValidationError -> {}
            EmailAuthEvent.SystemEvent.Success -> {}
        }
    }

    private suspend fun tryToAuthenticate() {
        delay(500)
        val credential = stateManager.getCredential()
        authService.createUserWithEmail(credential)
            .handleAppResult(
                onSuccess = {},
                onError = {}
            )
    }

}