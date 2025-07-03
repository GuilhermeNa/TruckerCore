package com.example.truckercore.view_model.view_models.verifying_email

import com.example.truckercore.model.errors.technical.TechnicalException
import com.example.truckercore.view_model._shared._base.reducer.BaseReducer
import com.example.truckercore.view_model._shared._base.reducer.ReducerResult
import com.example.truckercore.view_model.view_models.verifying_email.effect.VerifyingEmailEffect
import com.example.truckercore.view_model.view_models.verifying_email.effect.VerifyingEmailSystemEffect
import com.example.truckercore.view_model.view_models.verifying_email.effect.VerifyingEmailUiEffect
import com.example.truckercore.view_model.view_models.verifying_email.event.VerifyingEmailEvent
import com.example.truckercore.view_model.view_models.verifying_email.event.VerifyingEmailSystemEvent
import com.example.truckercore.view_model.view_models.verifying_email.event.VerifyingEmailUiEvent
import com.example.truckercore.view_model.view_models.verifying_email.state.VerifyingEmailState

class VerifyingEmailReducer :
    BaseReducer<VerifyingEmailEvent, VerifyingEmailState, VerifyingEmailEffect>() {

    override fun reduce(
        state: VerifyingEmailState,
        event: VerifyingEmailEvent
    ): ReducerResult<VerifyingEmailState, VerifyingEmailEffect> = when (event) {
        is VerifyingEmailSystemEvent -> handleSystemEvent(state, event)
        is VerifyingEmailUiEvent -> handleUiEvent(event)
        else -> throw TechnicalException.NotImplemented()
    }

    private fun handleSystemEvent(state: VerifyingEmailState, event: VerifyingEmailSystemEvent) =
        when (event) {
            is VerifyingEmailSystemEvent.Initialize -> {
                val newState = state.initialize(event.email)
                val systemEffect = VerifyingEmailSystemEffect.LaunchSendEmailTask
                resultWithStateAndEffect(newState, systemEffect)
            }
            VerifyingEmailSystemEvent.InitializationError -> {
                val uiEffect = VerifyingEmailUiEffect.NavigateToErrorActivity
                resultWithEffect(uiEffect)
            }
            VerifyingEmailSystemEvent.EmailSentSuccess -> {
                val newState = state.waitingVerification()
                val systemEffect = VerifyingEmailSystemEffect.LaunchCheckEmailTask
                resultWithStateAndEffect(newState, systemEffect)
            }
            VerifyingEmailSystemEvent.EmailSendCriticalError -> {

            }
            VerifyingEmailSystemEvent.EmailSendFailedNoConnection -> TODO()

          /*  is VerifyingEmailEvent.SystemEvent.InitializationTask.Success -> {
                val newState = state.initialize(event.email)
                val newEffect = VerifyingEmailEffect.SystemEffect.ExecuteVerificationTask
                resultWithStateAndEffect(newState, newEffect)
            }

            VerifyingEmailEvent.SystemEvent.InitializationTask.CriticalError -> {
                val newEffect = VerifyingEmailEffect.UiEffect.NavigateToNotification
                resultWithEffect(newEffect)
            }

            is VerifyingEmailEvent.SystemEvent.VerificationTask.Success -> {
                val newState = state.verified()
                val newEffect = VerifyingEmailEffect.UiEffect.TransitionToEnd
                resultWithStateAndEffect(newState, newEffect)
            }

            VerifyingEmailEvent.SystemEvent.VerificationTask.CriticalError -> {
                val newEffect = VerifyingEmailEffect.UiEffect.NavigateToNotification
                resultWithEffect(newEffect)
            }

            VerifyingEmailEvent.SystemEvent.CounterTask.TimeOut -> {
                val newState = state.timeout()
                resultWithState(newState)
            }*/

        }

    private fun handleUiEvent(event: VerifyingEmailUiEvent) =
        when (event) {
            VerifyingEmailEvent.UiEvent.Click.Retry -> {
                val newEffect = VerifyingEmailEffect.SystemEffect.ExecuteVerificationTask
                resultWithEffect(newEffect)
            }

            VerifyingEmailEvent.UiEvent.Click.CreateAnother -> {
                val newEffect = VerifyingEmailEffect.UiEffect.NavigateToUserName
                resultWithEffect(newEffect)
            }

            VerifyingEmailEvent.UiEvent.TransitionEnd -> {
                val newEffect = VerifyingEmailEffect.UiEffect.NavigateToUserName
                resultWithEffect(newEffect)
            }
        }



}