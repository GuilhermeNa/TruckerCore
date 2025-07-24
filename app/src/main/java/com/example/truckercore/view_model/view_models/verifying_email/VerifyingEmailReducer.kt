package com.example.truckercore.view_model.view_models.verifying_email

import com.example.truckercore.view_model._shared._base.reducer.BaseReducer
import com.example.truckercore.view_model._shared._base.reducer.ReducerResult
import com.example.truckercore.view_model.view_models.verifying_email.effect.VerifyingEmailEffect
import com.example.truckercore.view_model.view_models.verifying_email.effect.VerifyingEmailSystemEffect
import com.example.truckercore.view_model.view_models.verifying_email.effect.VerifyingEmailUiEffect
import com.example.truckercore.view_model.view_models.verifying_email.event.VerifyingEmailClickEvent
import com.example.truckercore.view_model.view_models.verifying_email.event.VerifyingEmailEvent
import com.example.truckercore.view_model.view_models.verifying_email.event.VerifyingEmailInitializationEvent
import com.example.truckercore.view_model.view_models.verifying_email.event.VerifyingEmailSendEmailEvent
import com.example.truckercore.view_model.view_models.verifying_email.event.VerifyingEmailSystemEvent
import com.example.truckercore.view_model.view_models.verifying_email.event.VerifyingEmailTransitionEvent
import com.example.truckercore.view_model.view_models.verifying_email.event.VerifyingEmailUiEvent
import com.example.truckercore.view_model.view_models.verifying_email.event.VerifyingEmailVerificationEvent
import com.example.truckercore.view_model.view_models.verifying_email.state.VerifyingEmailState

class VerifyingEmailReducer :
    BaseReducer<VerifyingEmailEvent, VerifyingEmailState, VerifyingEmailEffect>() {

    override fun reduce(
        state: VerifyingEmailState,
        event: VerifyingEmailEvent
    ): ReducerResult<VerifyingEmailState, VerifyingEmailEffect> = when (event) {
        is VerifyingEmailSystemEvent -> handleSystemEvent(state, event)
        is VerifyingEmailUiEvent -> handleUiEvent(event)
        else -> throw NotImplementedError()
    }

    // --- System Events ---
    private fun handleSystemEvent(
        state: VerifyingEmailState,
        event: VerifyingEmailSystemEvent
    ) = when (event) {
        is VerifyingEmailInitializationEvent.Success -> applyInitializationState(state, event)
        VerifyingEmailInitializationEvent.Error -> sendNavigateToErrorEffect()

        VerifyingEmailSendEmailEvent.Success -> applyWaitingForVerificationState(state)
        VerifyingEmailSendEmailEvent.CriticalError -> sendNavigateToErrorEffect()
        VerifyingEmailSendEmailEvent.NoConnection -> applyNoConnectionState(state)

        VerifyingEmailVerificationEvent.Success -> applyVerifiedState(state)
        VerifyingEmailVerificationEvent.CriticalError -> sendNavigateToErrorEffect()
        VerifyingEmailVerificationEvent.Timeout -> sendShowBottomSheetEffect()
        else -> throw NotImplementedError()
    }

    // --- UI Events ---
    private fun handleUiEvent(event: VerifyingEmailUiEvent) = when (event) {
        VerifyingEmailClickEvent.OnCheckConnection -> launchSendEmailTaskEffect()
        VerifyingEmailClickEvent.OnCreateNewAccount -> sendNavigateToNewEmailEffect()
        VerifyingEmailClickEvent.OnRetry -> launchSendEmailTaskEffect()
        VerifyingEmailTransitionEvent.TransitionComplete -> sendNavigateToCreateNameEffect()
        else -> throw NotImplementedError()
    }

    // --- State-only reducers ---
    private fun applyVerifiedState(
        state: VerifyingEmailState
    ): ReducerResult<VerifyingEmailState, VerifyingEmailEffect> {
        val newState = state.verified()
        return resultWithState(newState)
    }

    private fun applyNoConnectionState(
        state: VerifyingEmailState
    ): ReducerResult<VerifyingEmailState, VerifyingEmailEffect> {
        val newState = state.noConnection()
        return resultWithState(newState)
    }

    private fun applyWaitingForVerificationState(
        state: VerifyingEmailState
    ): ReducerResult<VerifyingEmailState, VerifyingEmailEffect> {
        val newState = state.waitingVerification()
        val effect = VerifyingEmailSystemEffect.LaunchCheckEmailTask
        return resultWithStateAndEffect(newState, effect)
    }

    private fun applyInitializationState(
        state: VerifyingEmailState,
        event: VerifyingEmailInitializationEvent.Success
    ): ReducerResult<VerifyingEmailState, VerifyingEmailEffect> {
        val newState = state.initialize(event.email)
        val effect = VerifyingEmailSystemEffect.LaunchSendEmailTask
        return resultWithStateAndEffect(newState, effect)
    }

    // --- Effect-only reducers ---
    private fun sendNavigateToErrorEffect(): ReducerResult<VerifyingEmailState, VerifyingEmailEffect> {
        return resultWithEffect(VerifyingEmailUiEffect.NavigateToErrorActivity)
    }

    private fun sendShowBottomSheetEffect(): ReducerResult<VerifyingEmailState, VerifyingEmailEffect> {
        return resultWithEffect(VerifyingEmailUiEffect.ShowBottomSheet)
    }

    private fun sendNavigateToCreateNameEffect(): ReducerResult<VerifyingEmailState, VerifyingEmailEffect> {
        return resultWithEffect(VerifyingEmailUiEffect.NavigateToCreateNameFragment)
    }

    private fun sendNavigateToNewEmailEffect(): ReducerResult<VerifyingEmailState, VerifyingEmailEffect> {
        return resultWithEffect(VerifyingEmailUiEffect.NavigateToCreateNewEmailFragment)
    }

    private fun launchSendEmailTaskEffect(): ReducerResult<VerifyingEmailState, VerifyingEmailEffect> {
        return resultWithEffect(VerifyingEmailSystemEffect.LaunchSendEmailTask)
    }

}