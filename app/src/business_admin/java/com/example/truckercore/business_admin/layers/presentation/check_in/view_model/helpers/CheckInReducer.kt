package com.example.truckercore.business_admin.layers.presentation.check_in.view_model.helpers

import com.example.truckercore.layers.presentation.base.reducer.Reducer
import com.example.truckercore.layers.presentation.base.reducer.ReducerResult

class CheckInReducer : Reducer<CheckInEvent, CheckInState, CheckInEffect>() {

    override fun reduce(
        state: CheckInState,
        event: CheckInEvent
    ): ReducerResult<CheckInState, CheckInEffect> = when (event) {
        CheckInEvent.Initialize -> handleInitializeEvent(state)
        is CheckInEvent.CheckAccessTask -> handleCheckAccessEvent(state, event)
        is CheckInEvent.CreateAccessTask -> handleCreateAccessEvent(state, event)
        is CheckInEvent.Retry -> handleRetryEvent(state, event)
    }

    //----------------------------------------------------------------------------------------------
    //  Handle Initialize Event
    //----------------------------------------------------------------------------------------------
    private fun handleInitializeEvent(
        state: CheckInState
    ) = ReducerResult.StateAndEffect(
        state = state.loading(),
        effect = CheckInEffect.LaunchCheckAccessTask
    )

    //----------------------------------------------------------------------------------------------
    //  Handle Check Access Event
    //----------------------------------------------------------------------------------------------
    private fun handleCheckAccessEvent(
        state: CheckInState,
        event: CheckInEvent.CheckAccessTask
    ) = ReducerResult.StateAndEffect(
        state = event.toNewState(state),
        effect = event.toNewEffect()
    )

    private fun CheckInEvent.CheckAccessTask.toNewState(state: CheckInState) =
        if (isAccessUnregistered) state.loading()
        else state.idle()

    private fun CheckInEvent.CheckAccessTask.toNewEffect() = when (this) {
        CheckInEvent.CheckAccessTask.Failure -> CheckInEffect.Navigation.ToError
        CheckInEvent.CheckAccessTask.NoConnection -> CheckInEffect.Navigation.ToNoConnection
        CheckInEvent.CheckAccessTask.Registered -> CheckInEffect.Navigation.ToMain
        CheckInEvent.CheckAccessTask.Unregistered -> CheckInEffect.LaunchCreateAccessTask
    }

    //----------------------------------------------------------------------------------------------
    //  Handle Create Access Event
    //----------------------------------------------------------------------------------------------
    private fun handleCreateAccessEvent(
        state: CheckInState,
        event: CheckInEvent.CreateAccessTask
    ) = ReducerResult.StateAndEffect(
        state = state.idle(),
        effect = event.toNewEffect()
    )

    private fun CheckInEvent.CreateAccessTask.toNewEffect() = when (this) {
        CheckInEvent.CreateAccessTask.Complete -> CheckInEffect.Navigation.ToMain
        CheckInEvent.CreateAccessTask.Failure -> CheckInEffect.Navigation.ToError
        CheckInEvent.CreateAccessTask.NoConnection -> CheckInEffect.Navigation.ToNoConnection
    }

    //----------------------------------------------------------------------------------------------
    //  Handle Retry Event
    //----------------------------------------------------------------------------------------------
    private fun handleRetryEvent(
        state: CheckInState,
        event: CheckInEvent.Retry
    ) = ReducerResult.StateAndEffect(
        state = state.loading(),
        effect = event.toNewEffect()
    )

    private fun CheckInEvent.Retry.toNewEffect() = when (this) {
        CheckInEvent.Retry.CheckAccess -> CheckInEffect.LaunchCheckAccessTask
        CheckInEvent.Retry.CreateAccess -> CheckInEffect.LaunchCreateAccessTask
    }

}