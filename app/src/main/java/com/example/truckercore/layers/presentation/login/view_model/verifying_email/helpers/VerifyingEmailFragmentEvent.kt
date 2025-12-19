package com.example.truckercore.layers.presentation.login.view_model.verifying_email.helpers

import com.example.truckercore.layers.presentation.base.contracts.Event

/**
 * Represents all possible events that can be emitted by the UI layer or internally by the
 * ViewModel during the email verification flow.
 *
 * These events are consumed by the [VerifyingEmailFragmentReducer], which transforms them
 * into UI states or one-shot effects handled by the fragment.
 *
 * Event categories:
 *  - User actions (Click)
 *  - Task results (GetEmailTask, SendEmailTask, VerifyEmailTask)
 *  - System-driven events (Timeout, RetryTask)
 *  - Animation/UI completion events (VerifiedUiTransitionEnd)
 *
 * Each event corresponds to a meaningful transition in the state machine, ensuring
 * predictable and testable behavior.
 */
sealed class VerifyingEmailFragmentEvent : Event {

    // ---------------------------------------------------------------------------------------------
    // UI / Animation Events
    // ---------------------------------------------------------------------------------------------
    /**
     * Fired when the UI transition animation shown after the email is verified has finished.
     * Signals to the ViewModel that navigation can proceed.
     */
    data object VerifiedUiTransitionEnd : VerifyingEmailFragmentEvent()


    // ---------------------------------------------------------------------------------------------
    // System Events
    // ---------------------------------------------------------------------------------------------
    /**
     * Fired when the countdown timer completes, meaning the user is allowed to retry sending
     * a verification email or the timeout logic needs to be applied.
     */
    data object Timeout : VerifyingEmailFragmentEvent()

    /**
     * Fired when the user retries an operation after encountering a network or backend failure.
     * Typically used after "No Connection" screens.
     */
    data object RetryTask : VerifyingEmailFragmentEvent()

    // ---------------------------------------------------------------------------------------------
    // User Interaction Events
    // ---------------------------------------------------------------------------------------------
    /**
     * Events triggered directly from user interactions (button clicks).
     */
    sealed class Click : VerifyingEmailFragmentEvent() {
        data object SendEmailButton : Click()
        data object NewEmailButton : Click()
    }

    // ---------------------------------------------------------------------------------------------
    // Task: Fetch User Email
    // ---------------------------------------------------------------------------------------------
    /**
     * Events related to the use case responsible for retrieving the user's stored email.
     */
    sealed class GetEmailTask : VerifyingEmailFragmentEvent() {

        /** The email was successfully retrieved. */
        data object Complete : GetEmailTask()

        /** The operation failed due to an unexpected error. */
        data object Failure : GetEmailTask()

        /** No email found locally (user hasn't registered or data was cleared). */
        data object NotFound : GetEmailTask()
    }

    // ---------------------------------------------------------------------------------------------
    // Task: Send Verification Email
    // ---------------------------------------------------------------------------------------------
    /**
     * Events emitted after attempting to send a verification email to the user.
     */
    sealed class SendEmailTask : VerifyingEmailFragmentEvent() {
        /** Email successfully sent. */
        data object Complete : SendEmailTask()

        /** Sending email failed due to an unexpected error. */
        data object Failure : SendEmailTask()

        /** Operation failed due to network connectivity issues. */
        data object NoConnection : SendEmailTask()
    }

    // ---------------------------------------------------------------------------------------------
    // Task: Verify Email (Polling / Validation)
    // ---------------------------------------------------------------------------------------------
    /**
     * Events representing the result of checking whether the user's email is verified.
     * These events are typically emitted by a long-running polling operation.
     */
    sealed class VerifyEmailTask : VerifyingEmailFragmentEvent() {
        /** Email is confirmed as verified. */
        data object Complete : VerifyEmailTask()

        /** Verification failed due to an unexpected backend or authentication error. */
        data object Failure : VerifyEmailTask()

        /** Verification failed due to network issues. */
        data object NoConnection : VerifyEmailTask()
    }

}