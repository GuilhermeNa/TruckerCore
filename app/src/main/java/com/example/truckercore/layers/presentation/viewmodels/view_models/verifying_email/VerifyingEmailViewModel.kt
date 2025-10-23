package com.example.truckercore.layers.presentation.viewmodels.view_models.verifying_email

import androidx.lifecycle.ViewModel
import com.example.truckercore.core.expressions.getOrNull
import com.example.truckercore.core.expressions.launch
import com.example.truckercore.core.expressions.logEvent
import com.example.truckercore.data.modules.authentication.manager.AuthManager
import com.example.truckercore.domain._shared.expressions.handleResult
import com.example.truckercore.domain._shared.expressions.mapResult
import com.example.truckercore.domain.view_models.verifying_email.event.VerifyingEmailClickEvent
import com.example.truckercore.domain.view_models.verifying_email.event.VerifyingEmailEvent
import com.example.truckercore.domain.view_models.verifying_email.event.VerifyingEmailInitializationEvent
import com.example.truckercore.domain.view_models.verifying_email.event.VerifyingEmailSendEmailEvent
import com.example.truckercore.domain.view_models.verifying_email.event.VerifyingEmailVerificationEvent
import com.example.truckercore.domain.view_models.verifying_email.reducer.VerifyingEmailReducer
import com.example.truckercore.domain.view_models.verifying_email.state.VerifyingEmailState
import com.example.truckercore.domain.view_models.verifying_email.use_cases.SendVerificationEmailViewUseCase

private typealias SuccessOnInitialization = VerifyingEmailInitializationEvent.Success
private typealias ErrorOnInitialization = VerifyingEmailInitializationEvent.Error

private typealias OnRetryCLicked = VerifyingEmailClickEvent.OnRetry
private typealias OnCreateNewEmailClicked = VerifyingEmailClickEvent.OnCreateNewAccount

class VerifyingEmailViewModel(
    private val authManager: AuthManager,
    private val sendEmailUseCase: SendVerificationEmailViewUseCase,
    private val verifyEmailUseCase: com.example.truckercore.presentation.viewmodels.view_models.verifying_email.use_cases.VerifyEmailViewUseCase
) : ViewModel() {

    val counterFlow = verifyEmailUseCase.counterFlow

    private val stateManager =
        com.example.truckercore.presentation.viewmodels._shared._base.managers.StateManagerII(
            VerifyingEmailState()
        )
    val stateFLow = stateManager.stateFlow

    private val effectManager =
        com.example.truckercore.presentation.viewmodels._shared._base.managers.EffectManagerII<com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailUiEffect>()
    val effectFlow = effectManager.effectFlow

    private val reducer = VerifyingEmailReducer()

    //----------------------------------------------------------------------------------------------
    init {
        fetchEmail()
    }

    private fun fetchEmail() {
        val initializationEvent = authManager.getUserEmail().getOrNull()?.let { email ->
            SuccessOnInitialization(email)
        } ?: ErrorOnInitialization
        onEvent(initializationEvent)
    }

    private fun onEvent(event: VerifyingEmailEvent) {
        logEvent(this, event)
        reducer.reduce(stateManager.currentState(), event).handleResult(
            state = { stateManager.update(it) },
            effect = { handleEffect(it) }
        )
    }

    private fun handleEffect(effect: com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailEffect) {
        when (effect) {
            is com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailSystemEffect -> when (effect) {
                com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailSystemEffect.LaunchSendEmailTask -> launchSendEmailTask()
                com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailSystemEffect.LaunchCheckEmailTask -> launchCheckEmailTask()
            }

            is com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailUiEffect -> effectManager.trySend(effect)
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

}