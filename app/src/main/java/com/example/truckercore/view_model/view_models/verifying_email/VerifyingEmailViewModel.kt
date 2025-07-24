package com.example.truckercore.view_model.view_models.verifying_email

 import androidx.lifecycle.ViewModel
import com.example.truckercore._shared.expressions.getOrNull
import com.example.truckercore._shared.expressions.launch
import com.example.truckercore._shared.expressions.logEvent
import com.example.truckercore.model.modules.authentication.manager.AuthManager
import com.example.truckercore.view_model._shared._base.managers.EffectManagerII
import com.example.truckercore.view_model._shared._base.managers.StateManagerII
import com.example.truckercore.view_model._shared.expressions.handleResult
import com.example.truckercore.view_model._shared.expressions.mapResult
import com.example.truckercore.view_model.view_models.verifying_email.effect.VerifyingEmailEffect
import com.example.truckercore.view_model.view_models.verifying_email.effect.VerifyingEmailSystemEffect
import com.example.truckercore.view_model.view_models.verifying_email.effect.VerifyingEmailUiEffect
import com.example.truckercore.view_model.view_models.verifying_email.event.VerifyingEmailClickEvent
import com.example.truckercore.view_model.view_models.verifying_email.event.VerifyingEmailEvent
import com.example.truckercore.view_model.view_models.verifying_email.event.VerifyingEmailInitializationEvent
import com.example.truckercore.view_model.view_models.verifying_email.event.VerifyingEmailSendEmailEvent
import com.example.truckercore.view_model.view_models.verifying_email.event.VerifyingEmailVerificationEvent
import com.example.truckercore.view_model.view_models.verifying_email.state.VerifyingEmailState
import com.example.truckercore.view_model.view_models.verifying_email.use_cases.SendVerificationEmailViewUseCase
import com.example.truckercore.view_model.view_models.verifying_email.use_cases.VerifyEmailViewUseCase

private typealias SuccessOnInitialization = VerifyingEmailInitializationEvent.Success
private typealias ErrorOnInitialization = VerifyingEmailInitializationEvent.Error

private typealias OnRetryCLicked = VerifyingEmailClickEvent.OnRetry
private typealias OnCreateNewEmailClicked = VerifyingEmailClickEvent.OnCreateNewAccount
private typealias OnCheckConnectionClicked = VerifyingEmailClickEvent.OnCheckConnection

class VerifyingEmailViewModel(
    private val authManager: AuthManager,
    private val sendEmailUseCase: SendVerificationEmailViewUseCase,
    private val verifyEmailUseCase: VerifyEmailViewUseCase
) : ViewModel() {

    val counterFlow = verifyEmailUseCase.counterFlow

    private val stateManager = StateManagerII(VerifyingEmailState())
    val stateFLow = stateManager.stateFlow

    private val effectManager = EffectManagerII<VerifyingEmailUiEffect>()
    val effectFlow = effectManager.effectFlow

    private val reducer = VerifyingEmailReducer()

    //----------------------------------------------------------------------------------------------
    fun initialize() {
        val email = authManager.getUserEmail().getOrNull()

        val initializationEvent =
            if (email != null) SuccessOnInitialization(email)
            else ErrorOnInitialization

        onEvent(initializationEvent)
    }

    private fun onEvent(event: VerifyingEmailEvent) {
        logEvent(this, event)
        reducer.reduce(stateManager.currentState(), event).handleResult(
            state = { stateManager.update(it) },
            effect = { handleEffect(it) }
        )
    }

    private fun handleEffect(effect: VerifyingEmailEffect) {
        // Inner Functions --
        fun handleSystemEffect(systemEffect: VerifyingEmailSystemEffect) {
            when (systemEffect) {
                VerifyingEmailSystemEffect.LaunchSendEmailTask -> launchSendEmailTask()
                VerifyingEmailSystemEffect.LaunchCheckEmailTask -> launchCheckEmailTask()
            }
        }

        // --
        when (effect) {
            is VerifyingEmailSystemEffect -> handleSystemEffect(effect)
            is VerifyingEmailUiEffect -> effectManager.trySend(effect)
        }
    }

    private fun launchSendEmailTask(): Unit = launch {
        val sendEmailEvent = sendEmailUseCase().mapResult(
            onSuccess = { VerifyingEmailSendEmailEvent.Success },
            onCriticalError = { VerifyingEmailSendEmailEvent.CriticalError },
            onRecoverableError = { VerifyingEmailSendEmailEvent.NoConnection }
        )
        onEvent(sendEmailEvent)
    }

    private fun launchCheckEmailTask(): Unit = launch {
        val verificationEvent = verifyEmailUseCase().mapResult(
            onSuccess = { VerifyingEmailVerificationEvent.Success },
            onCriticalError = { VerifyingEmailVerificationEvent.CriticalError },
            onRecoverableError = { VerifyingEmailVerificationEvent.Timeout }
        )
        onEvent(verificationEvent)
    }

    fun retry() {
        onEvent(OnRetryCLicked)
    }

    fun createAnotherEmail() {
        onEvent(OnCreateNewEmailClicked)
    }

    fun checkConnection() {
        onEvent(OnCheckConnectionClicked)
    }

}