package com.example.truckercore.layers.presentation.nav_login.view_model.verifying_email.reducer

import com.example.truckercore.layers.presentation.base.reducer.Reducer
import com.example.truckercore.layers.presentation.nav_login.view_model.verifying_email.effect.VerifyingEmailFragmentEffect
import com.example.truckercore.layers.presentation.nav_login.view_model.verifying_email.event.VerifyingEmailFragmentEvent
import com.example.truckercore.layers.presentation.nav_login.view_model.verifying_email.state.VerifyingEmailFragmentState

class VerifyingEmailFragmentReducer :
    Reducer<VerifyingEmailFragmentEvent, VerifyingEmailFragmentState, VerifyingEmailFragmentEffect>() {

    override fun reduce(
        state: VerifyingEmailFragmentState,
        event: VerifyingEmailFragmentEvent
    ) = when (event) {
        VerifyingEmailFragmentEvent.Timeout ->
            triggerCancelVerifyEmailTaskAndUpdateState()

        VerifyingEmailFragmentEvent.RetryTask -> triggerRetryEffect(state)

        VerifyingEmailFragmentEvent.VerifiedUiTransitionEnd ->
            resultWithEffect(VerifyingEmailFragmentEffect.Navigation.ToProfile)

        is VerifyingEmailFragmentEvent.Click ->
            handleClickEvent(state, event)

        is VerifyingEmailFragmentEvent.GetEmailTask ->
            handleGetEmailTaskEvents(state, event)

        is VerifyingEmailFragmentEvent.SendEmailTask ->
            handleSendEmailTaskEvents(state, event)

        is VerifyingEmailFragmentEvent.VerifyEmailTask ->
            handleVerifyEmailTaskEvents(state, event)

    }

    private fun triggerRetryEffect(state: VerifyingEmailFragmentState) =
        when (state) {
            VerifyingEmailFragmentState.SendingEmail ->
                resultWithEffect(VerifyingEmailFragmentEffect.LaunchSendEmailTask)

            VerifyingEmailFragmentState.VerifyingEmail ->
                resultWithEffect(VerifyingEmailFragmentEffect.LaunchVerifyEmailTask)

            else -> throw IllegalStateException()
        }

    private fun triggerCancelVerifyEmailTaskAndUpdateState() =
        resultWithStateAndEffect(
            state = VerifyingEmailFragmentState.EmailFound,
            effect = VerifyingEmailFragmentEffect.CancelVerifyEmailTask
        )

    //----------------------------------------------------------------------------------------------
    // Handle Click
    //----------------------------------------------------------------------------------------------
    private fun handleClickEvent(
        state: VerifyingEmailFragmentState,
        event: VerifyingEmailFragmentEvent.Click
    ) = when (event) {
        VerifyingEmailFragmentEvent.Click.SendEmailButton ->
            triggerSendEmailTaskAndUpdateState(state)

        VerifyingEmailFragmentEvent.Click.NewEmailButton ->
            resultWithEffect(VerifyingEmailFragmentEffect.Navigation.ToNewEmail)

    }

    private fun triggerSendEmailTaskAndUpdateState(state: VerifyingEmailFragmentState) =
        resultWithStateAndEffect(
            state = state.sendingEmail(),
            effect = VerifyingEmailFragmentEffect.LaunchSendEmailTask
        )

    //----------------------------------------------------------------------------------------------
    // Handle GetEmailTask
    //----------------------------------------------------------------------------------------------
    private fun handleGetEmailTaskEvents(
        state: VerifyingEmailFragmentState,
        event: VerifyingEmailFragmentEvent.GetEmailTask
    ) = when (event) {
        VerifyingEmailFragmentEvent.GetEmailTask.Complete -> {
            val newState = state.emailFound()
            resultWithState(newState)
        }

        VerifyingEmailFragmentEvent.GetEmailTask.NotFound -> {
            val newEffect = VerifyingEmailFragmentEffect.Navigation.ToLogin
            resultWithEffect(newEffect)
        }

        VerifyingEmailFragmentEvent.GetEmailTask.Failure ->
            triggerNavigateToNotification()
    }

    private fun triggerNavigateToNotification() =
        resultWithEffect(VerifyingEmailFragmentEffect.Navigation.ToNotification)

    //----------------------------------------------------------------------------------------------
    // Handle SendEmailTask
    //----------------------------------------------------------------------------------------------
    private fun handleSendEmailTaskEvents(
        state: VerifyingEmailFragmentState,
        event: VerifyingEmailFragmentEvent.SendEmailTask
    ) = when (event) {
        VerifyingEmailFragmentEvent.SendEmailTask.Complete ->
            resultWithState(state.verifyingEmail())

        VerifyingEmailFragmentEvent.SendEmailTask.Failure ->
            triggerNavigateToNotification()

        VerifyingEmailFragmentEvent.SendEmailTask.NoConnection ->
            triggerNavigateToNoConnection()
    }

    private fun triggerNavigateToNoConnection() =
        resultWithEffect(VerifyingEmailFragmentEffect.Navigation.ToNoConnection)

    //----------------------------------------------------------------------------------------------
    // Handle VerifyEmailTask
    //----------------------------------------------------------------------------------------------
    private fun handleVerifyEmailTaskEvents(
        state: VerifyingEmailFragmentState,
        event: VerifyingEmailFragmentEvent.VerifyEmailTask
    ) = when (event) {
        VerifyingEmailFragmentEvent.VerifyEmailTask.Complete ->
            resultWithState(state.emailVerified())

        VerifyingEmailFragmentEvent.VerifyEmailTask.Failure ->
            triggerNavigateToNotification()

        VerifyingEmailFragmentEvent.VerifyEmailTask.NoConnection ->
            triggerNavigateToNoConnection()
    }

}