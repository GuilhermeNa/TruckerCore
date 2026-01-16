package com.example.truckercore.layers.presentation.login.view_model.verifying_email.helpers

import com.example.truckercore.layers.presentation.base.reducer.Reducer
import com.example.truckercore.layers.presentation.base.reducer.ReducerResult

/**
 * Reducer implementation responsible for transforming incoming UI/business events
 * into new UI states and/or side effects for the Verifying Email feature.
 *
 * This reducer:
 *  - Ensures state transitions obey the allowed state machine rules
 *  - Produces one-shot effects for the ViewModel (e.g., navigation, starting tasks)
 *  - Decouples the ViewModel from complex state logic
 *
 * The reducer never performs I/O or suspend operations; it only expresses
 * deterministic transitions based on the current state and event.
 */
class VerifyingEmailFragmentReducer :
    Reducer<VerifyingEmailFragmentEvent, VerifyingEmailFragmentState, VerifyingEmailFragmentEffect>() {

    /**
     * Core state machine function.
     *
     * Receives the current UI state and a new UI/business event,
     * then returns a [Result] describing:
     *  - A new state (if any)
     *  - A side effect (if any)
     *
     * Every event is delegated to a specialized handler to keep the reducer modular.
     */
    override fun reduce(
        state: VerifyingEmailFragmentState,
        event: VerifyingEmailFragmentEvent
    ) = when (event) {

        // Countdown timeout → cancel verification flow and return to "EmailFound" state
        VerifyingEmailFragmentEvent.Timeout ->
            triggerCancelVerifyEmailTaskAndUpdateState(state)

        // Retry logic depends on the current state (sending or verifying)
        VerifyingEmailFragmentEvent.RetryTask ->
            triggerRetryEffect(state)

        // After the success animation completes, navigate to profile setup
        VerifyingEmailFragmentEvent.VerifiedUiTransitionEnd ->
            resultWithEffect(VerifyingEmailFragmentEffect.Navigation.ToProfile)

        // Button clicks
        is VerifyingEmailFragmentEvent.Click ->
            handleClickEvent(state, event)

        // GetUserEmail result events
        is VerifyingEmailFragmentEvent.GetEmailTask ->
            handleGetEmailTaskEvents(state, event)

        // SendEmailVerification result events
        is VerifyingEmailFragmentEvent.SendEmailTask ->
            handleSendEmailTaskEvents(state, event)

        // VerifyEmailPolling result events
        is VerifyingEmailFragmentEvent.VerifyEmailTask ->
            handleVerifyEmailTaskEvents(state, event)
    }

    // ---------------------------------------------------------------------------------------------
    // Retry & Timeout Handling
    // ---------------------------------------------------------------------------------------------
    /**
     * Determines which retry task must be relaunched depending on the current state.
     *
     * - If the UI was sending the email → retry sending email
     * - If the UI was verifying email → restart verification
     */
    private fun triggerRetryEffect(state: VerifyingEmailFragmentState) =
        when (state) {
            VerifyingEmailFragmentState.SendingEmail ->
                resultWithEffect(VerifyingEmailFragmentEffect.LaunchSendEmailTask)

            VerifyingEmailFragmentState.VerifyingEmail ->
                resultWithEffect(VerifyingEmailFragmentEffect.LaunchVerifyEmailTask)

            else -> throw IllegalStateException("Retry not allowed from state: $state")
        }

    /**
     * Timeout event ends the verification process:
     *  - Cancels verification & countdown tasks
     *  - Returns user to the email screen (EmailFound)
     */
    private fun triggerCancelVerifyEmailTaskAndUpdateState(state: VerifyingEmailFragmentState) =
        resultWithStateAndEffect(
            state = state.emailFound(),
            effect = VerifyingEmailFragmentEffect.CancelVerifyEmailTask
        )

    // ---------------------------------------------------------------------------------------------
    // Click Events Handling
    // ---------------------------------------------------------------------------------------------
    private fun handleClickEvent(
        state: VerifyingEmailFragmentState,
        event: VerifyingEmailFragmentEvent.Click
    ) = when (event) {
        // User requests to send verification email
        VerifyingEmailFragmentEvent.Click.SendEmailButton ->
            triggerSendEmailTaskAndUpdateState(state)

        // User chooses to provide a new email
        VerifyingEmailFragmentEvent.Click.NewEmailButton ->
            resultWithEffect(VerifyingEmailFragmentEffect.Navigation.ToNewEmail)
    }

    /**
     * Transitions to SendingEmail state and triggers the SendEmail background task.
     */
    private fun triggerSendEmailTaskAndUpdateState(state: VerifyingEmailFragmentState) =
        resultWithStateAndEffect(
            state = state.sendingEmail(),
            effect = VerifyingEmailFragmentEffect.LaunchSendEmailTask
        )

    // ---------------------------------------------------------------------------------------------
    // GetEmailTask Handling
    // ---------------------------------------------------------------------------------------------
    private fun handleGetEmailTaskEvents(
        state: VerifyingEmailFragmentState,
        event: VerifyingEmailFragmentEvent.GetEmailTask
    ) = when (event) {

        // Email successfully retrieved → show email UI
        VerifyingEmailFragmentEvent.GetEmailTask.Complete -> {
            val newState = state.emailFound()
            resultWithState(newState)
        }

        // No email saved → redirect to Login
        VerifyingEmailFragmentEvent.GetEmailTask.NotFound ->
            resultWithEffect(VerifyingEmailFragmentEffect.Navigation.ToLogin)

        // Unexpected error → notification screen
        VerifyingEmailFragmentEvent.GetEmailTask.Failure ->
            triggerNavigateToNotification()
    }

    private fun triggerNavigateToNotification() =
        resultWithEffect(VerifyingEmailFragmentEffect.Navigation.ToNotification)

    // ---------------------------------------------------------------------------------------------
    // SendEmailTask Handling
    // ---------------------------------------------------------------------------------------------
    private fun handleSendEmailTaskEvents(
        state: VerifyingEmailFragmentState,
        event: VerifyingEmailFragmentEvent.SendEmailTask
    ) = when (event) {

        // Email successfully sent → start verification/countdown process
        VerifyingEmailFragmentEvent.SendEmailTask.Complete ->
          triggerVerifyEmailTaskAndUpdateState(state)

        // Unexpected error → notification screen
        VerifyingEmailFragmentEvent.SendEmailTask.Failure ->
            triggerNavigateToNotification()

        // No connection → go to dedicated NoConnection screen
        VerifyingEmailFragmentEvent.SendEmailTask.NoConnection ->
            triggerNavigateToNoConnection()
    }

    private fun triggerVerifyEmailTaskAndUpdateState(
        state: VerifyingEmailFragmentState
    ): ReducerResult<VerifyingEmailFragmentState, VerifyingEmailFragmentEffect> {
        val newState = state.verifyingEmail()
        val newEffect = VerifyingEmailFragmentEffect.LaunchVerifyEmailTask
        return resultWithStateAndEffect(newState, newEffect)
    }

    private fun triggerNavigateToNoConnection() =
        resultWithEffect(VerifyingEmailFragmentEffect.Navigation.ToNoConnection)

    // ---------------------------------------------------------------------------------------------
    // VerifyEmailTask Handling
    // ---------------------------------------------------------------------------------------------
    private fun handleVerifyEmailTaskEvents(
        state: VerifyingEmailFragmentState,
        event: VerifyingEmailFragmentEvent.VerifyEmailTask
    ) = when (event) {

        // Email verified → move to final state and UI animation begins
        VerifyingEmailFragmentEvent.VerifyEmailTask.Complete ->
            resultWithState(state.emailVerified())

        // Unexpected failure → notification screen
        VerifyingEmailFragmentEvent.VerifyEmailTask.Failure ->
            triggerNavigateToNotification()

        // Network failure → no-connection screen
        VerifyingEmailFragmentEvent.VerifyEmailTask.NoConnection ->
            triggerNavigateToNoConnection()
    }

}