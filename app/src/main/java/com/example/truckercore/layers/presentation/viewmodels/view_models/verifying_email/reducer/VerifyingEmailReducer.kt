package com.example.truckercore.layers.presentation.viewmodels.view_models.verifying_email.reducer

import com.example.truckercore.domain.view_models.verifying_email.event.VerifyingEmailClickEvent
import com.example.truckercore.domain.view_models.verifying_email.event.VerifyingEmailEvent
import com.example.truckercore.domain.view_models.verifying_email.event.VerifyingEmailInitializationEvent
import com.example.truckercore.domain.view_models.verifying_email.event.VerifyingEmailSendEmailEvent
import com.example.truckercore.domain.view_models.verifying_email.event.VerifyingEmailSystemEvent
import com.example.truckercore.domain.view_models.verifying_email.event.VerifyingEmailTransitionEvent
import com.example.truckercore.domain.view_models.verifying_email.event.VerifyingEmailUiEvent
import com.example.truckercore.domain.view_models.verifying_email.event.VerifyingEmailVerificationEvent
import com.example.truckercore.domain.view_models.verifying_email.state.VerifyingEmailState

class VerifyingEmailReducer :
    com.example.truckercore.presentation.viewmodels._shared._base.reducer.BaseReducer<VerifyingEmailEvent, VerifyingEmailState, com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailEffect>() {

    override fun reduce(
        state: VerifyingEmailState,
        event: VerifyingEmailEvent
    ): com.example.truckercore.presentation.viewmodels._shared._base.reducer.ReducerResult<VerifyingEmailState, com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailEffect> = when (event) {
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
        VerifyingEmailSendEmailEvent.NoConnection -> sendNavigateToNoConnectionEffect()

        VerifyingEmailVerificationEvent.Success -> applyVerifiedState(state)
        VerifyingEmailVerificationEvent.CriticalError -> sendNavigateToErrorEffect()
        VerifyingEmailVerificationEvent.Timeout -> sendShowBottomSheetEffect()
        else -> throw NotImplementedError()
    }

    // --- UI Events ---
    private fun handleUiEvent(event: VerifyingEmailUiEvent) = when (event) {
        VerifyingEmailClickEvent.OnCreateNewAccount -> sendNavigateToNewEmailEffect()
        VerifyingEmailClickEvent.OnRetry -> launchSendEmailTaskEffect()
        VerifyingEmailTransitionEvent.TransitionComplete -> sendNavigateToCreateNameEffect()
        else -> throw NotImplementedError()
    }

    // --- State-only reducers ---
    private fun applyVerifiedState(state: VerifyingEmailState):
            com.example.truckercore.presentation.viewmodels._shared._base.reducer.ReducerResult<VerifyingEmailState, com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailEffect> {
        val newState = state.verified()
        return resultWithState(newState)
    }

    private fun sendNavigateToNoConnectionEffect():
            com.example.truckercore.presentation.viewmodels._shared._base.reducer.ReducerResult<VerifyingEmailState, com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailEffect> {
        return resultWithEffect(com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailUiEffect.NavigateToNoConnectionFragment)
    }

    private fun applyWaitingForVerificationState(state: VerifyingEmailState):
            com.example.truckercore.presentation.viewmodels._shared._base.reducer.ReducerResult<VerifyingEmailState, com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailEffect> {
        val newState = state.waitingVerification()
        val effect = com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailSystemEffect.LaunchCheckEmailTask
        return resultWithStateAndEffect(newState, effect)
    }

    private fun applyInitializationState(
        state: VerifyingEmailState,
        event: VerifyingEmailInitializationEvent.Success
    ): com.example.truckercore.presentation.viewmodels._shared._base.reducer.ReducerResult<VerifyingEmailState, com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailEffect> {
        val newState = state.initialize(event.email)
        val effect = com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailSystemEffect.LaunchSendEmailTask
        return resultWithStateAndEffect(newState, effect)
    }

    // --- Effect-only reducers ---
    private fun sendNavigateToErrorEffect(): com.example.truckercore.presentation.viewmodels._shared._base.reducer.ReducerResult<VerifyingEmailState, com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailEffect> {
        return resultWithEffect(com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailUiEffect.NavigateToErrorActivity)
    }

    private fun sendShowBottomSheetEffect(): com.example.truckercore.presentation.viewmodels._shared._base.reducer.ReducerResult<VerifyingEmailState, com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailEffect> {
        return resultWithEffect(com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailUiEffect.ShowBottomSheet)
    }

    private fun sendNavigateToCreateNameEffect(): com.example.truckercore.presentation.viewmodels._shared._base.reducer.ReducerResult<VerifyingEmailState, com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailEffect> {
        return resultWithEffect(com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailUiEffect.NavigateToCreateNameFragment)
    }

    private fun sendNavigateToNewEmailEffect(): com.example.truckercore.presentation.viewmodels._shared._base.reducer.ReducerResult<VerifyingEmailState, com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailEffect> {
        return resultWithEffect(com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailUiEffect.NavigateToCreateNewEmailFragment)
    }

    private fun launchSendEmailTaskEffect(): com.example.truckercore.presentation.viewmodels._shared._base.reducer.ReducerResult<VerifyingEmailState, com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailEffect> {
        return resultWithEffect(com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailSystemEffect.LaunchSendEmailTask)
    }

}