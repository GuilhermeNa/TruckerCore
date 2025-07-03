package com.example.truckercore.view_model.view_models.verifying_email

import androidx.lifecycle.ViewModel
import com.example.truckercore._shared.classes.AppResult
import com.example.truckercore._shared.expressions.getOrNull
import com.example.truckercore._shared.expressions.launch
import com.example.truckercore._shared.expressions.logEvent
import com.example.truckercore._shared.expressions.mapAppResult
import com.example.truckercore.model.modules.authentication.manager.AuthManager
import com.example.truckercore.view_model._shared._base.managers.EffectManagerII
import com.example.truckercore.view_model._shared._base.managers.StateManagerII
import com.example.truckercore.view_model._shared.expressions.get
import com.example.truckercore.view_model._shared.expressions.handleResult
import com.example.truckercore.view_model._shared.expressions.mapResult
import com.example.truckercore.view_model.view_models.verifying_email.effect.VerifyingEmailUiEffect
import com.example.truckercore.view_model.view_models.verifying_email.event.VerifyingEmailSystemEvent
import com.example.truckercore.view_model.view_models.verifying_email.event.VerifyingEmailUiEvent
import com.example.truckercore.view_model.view_models.verifying_email.state.VerifyingEmailState
import com.example.truckercore.view_model.view_models.verifying_email.use_cases.EmailValidation
import com.example.truckercore.view_model.view_models.verifying_email.use_cases.SendVerificationEmailViewUseCase
import com.example.truckercore.view_model.view_models.verifying_email.use_cases.VerifyEmailViewUseCase

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

        val event =
            if (email != null) VerifyingEmailSystemEvent.Initialize(email)
            else VerifyingEmailSystemEvent.InitializationError

        onSystemEvent(event)
    }

    private fun onSystemEvent(event: VerifyingEmailSystemEvent) {
        logEvent(this, event)
        reducer.reduce(stateManager.currentState(), event).handleResult {

        }
    }

    private fun sendVerificationEmail(): Unit = launch {
        sendEmailUseCase().mapResult(
            onSuccess = { },
            onCriticalError = { },
            onRecoverableError = { }
        )
    }

    private fun startVerification(): Unit = launch {
        val result = verifyEmailUseCase().get()
        val event = when (result) {
            EmailValidation.Valid -> TODO("Ativar animação de conclusão")
            EmailValidation.Error -> TODO("Navegar para activity de erro")
            EmailValidation.Timeout -> TODO("Exibir Dialog com opções")
        }
        onEvent(event)
    }



    private fun onUiEvent(uiEvent: VerifyingEmailUiEvent) {

    }

    fun transitionEnd() {
        onEvent(VerifyingEmailEvent.UiEvent.TransitionEnd)
    }

    fun retry() {
        onEvent(VerifyingEmailEvent.UiEvent.Click.Retry)
    }

    private fun onEvent(event: VerifyingEmailEvent) {
        logEvent(this, event)
        reducer.reduce(stateManager.currentState(), event).handleResult(
            state = { stateManager.update(it) },
            effect = ::handleEffect
        )
    }

    private fun handleEffect(effect: VerifyingEmailEffect) {
        when (effect) {
            VerifyingEmailEffect.SystemEffect.ExecuteVerificationTask -> startVerification()
            else -> effectManager.trySend(effect)
        }
    }


    private fun getEventFromResult(result: AppResult<Unit>) = result.mapAppResult(
        onSuccess = { VerifyingEmailEvent.SystemEvent.VerificationTask.Success },
        onError = { VerifyingEmailEvent.SystemEvent.VerificationTask.CriticalError }
    )

}