package com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth

import com.example.truckercore.core.expressions.launch
import com.example.truckercore.core.expressions.logEvent
import com.example.truckercore.domain._shared.expressions.mapResult
import com.example.truckercore.domain.view_models.email_auth.event.EmailAuthEvent
import com.example.truckercore.domain.view_models.email_auth.uiState.EmailAuthUiStateManager
import com.example.truckercore.domain.view_models.email_auth.use_case.AuthenticationViewUseCase
import kotlinx.coroutines.delay

class EmailAuthViewModel(
    private val authViewUseCase: AuthenticationViewUseCase
) : com.example.truckercore.presentation.viewmodels._shared._base.view_model.LoggerViewModel() {

    private val stateManager = EmailAuthUiStateManager()
    val state get() = stateManager.stateFlow

    private val effectManager =
        com.example.truckercore.presentation.viewmodels.view_models.email_auth.effect.EmailAuthEffectManager()
    val effect get() = effectManager.effectFlow

    //----------------------------------------------------------------------------------------------
    fun onEvent(event: EmailAuthEvent) = launch {
        logEvent(this@EmailAuthViewModel, event)
        when (event) {
            is EmailAuthEvent.UiEvent.Typing -> handleTypingEvent(event)
            is EmailAuthEvent.UiEvent.Click -> handleClickEvent(event)
            is EmailAuthEvent.SystemEvent -> handleSystemEvent(event)
        }
    }

    private fun handleTypingEvent(event: EmailAuthEvent.UiEvent.Typing) {
        when (event) {
            is EmailAuthEvent.UiEvent.Typing.EmailTextChange ->
                stateManager.updateComponentsOnEmailChange(event.text)

            is EmailAuthEvent.UiEvent.Typing.PasswordTextChange ->
                stateManager.updateComponentsOnPasswordChange(event.text)

            is EmailAuthEvent.UiEvent.Typing.ConfirmationTextChange ->
                stateManager.updateComponentsOnConfirmationChange(event.text)
        }
    }

    private fun handleClickEvent(uiEvent: EmailAuthEvent.UiEvent.Click) {
        when (uiEvent) {
            EmailAuthEvent.UiEvent.Click.Background ->
                effectManager.setClearFocusAndHideKeyboardEffect()

            EmailAuthEvent.UiEvent.Click.ButtonAlreadyHaveAccount ->
                effectManager.setNavigateToLoginEffect()

            is EmailAuthEvent.UiEvent.Click.ButtonCreate ->
                onEvent(EmailAuthEvent.SystemEvent.AuthTask.Executing)
        }
    }

    private suspend fun handleSystemEvent(event: EmailAuthEvent.SystemEvent) {
        when (event) {
            EmailAuthEvent.SystemEvent.AuthTask.Executing -> {
                stateManager.setCreatingState()
                tryToAuthenticate()
            }

            EmailAuthEvent.SystemEvent.AuthTask.Success -> {
                effectManager.setNavigateToVerifyEmailEffect()
                stateManager.setCreatedState()
            }

            EmailAuthEvent.SystemEvent.AuthTask.CriticalError ->
                effectManager.setNavigateToNotificationEffect()

            is EmailAuthEvent.SystemEvent.AuthTask.RecoverableError -> {
                stateManager.setIdleState()
                effectManager.setShowToastEffect(event.message)
            }
        }
    }

    private suspend fun tryToAuthenticate() {
        delay(500)
        val credential = stateManager.getCredential()
        val newEvent = authViewUseCase(credential).mapResult(
            onSuccess = { EmailAuthEvent.SystemEvent.AuthTask.Success },
            onCriticalError = { EmailAuthEvent.SystemEvent.AuthTask.CriticalError },
            onRecoverableError = { EmailAuthEvent.SystemEvent.AuthTask.RecoverableError(it.message) }
        )
        onEvent(newEvent)
    }

}