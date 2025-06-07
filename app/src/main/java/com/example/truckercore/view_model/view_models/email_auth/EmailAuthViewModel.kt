package com.example.truckercore.view_model.view_models.email_auth

import com.example.truckercore._shared.expressions.launch
import com.example.truckercore._shared.expressions.logEvent
import com.example.truckercore.view_model._shared._base.view_model.LoggerViewModel
import com.example.truckercore.view_model._shared.expressions.mapResult
import com.example.truckercore.view_model.view_models.email_auth.effect.EmailAuthEffectManager
import com.example.truckercore.view_model.view_models.email_auth.event.EmailAuthEvent
import com.example.truckercore.view_model.view_models.email_auth.uiState.EmailAuthUiStateManager
import com.example.truckercore.view_model.view_models.email_auth.use_case.AuthenticationViewUseCase
import kotlinx.coroutines.delay

class EmailAuthViewModel(private val authViewUseCase: AuthenticationViewUseCase) :
    LoggerViewModel() {

    private val stateManager = EmailAuthUiStateManager()
    val state get() = stateManager.stateFlow

    private val effectManager = EmailAuthEffectManager()
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

            EmailAuthEvent.SystemEvent.AuthTask.Success ->
                effectManager.setNavigateToVerifyEmailEffect()

            EmailAuthEvent.SystemEvent.AuthTask.CriticalError ->
                effectManager.setNavigateToNotification()

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