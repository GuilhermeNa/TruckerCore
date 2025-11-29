package com.example.truckercore.layers.presentation.nav_login.view_model.verifying_email.helpers

import com.example.truckercore.layers.presentation.base.contracts.State
import com.example.truckercore.layers.presentation.nav_login.view.fragments.verifying_email.VerifyingEmailFragmentStateHandler

/**
 * Represents the complete state machine for the Email Verification screen.
 *
 * Each state corresponds to a distinct UI configuration, and transitions between states
 * are controlled internally through guarded helper functions (e.g., [sendingEmail]),
 * ensuring the state machine only evolves in valid, predictable ways.
 *
 * This sealed hierarchy is consumed by:
 *  - [VerifyingEmailFragmentStateHandler] to update the UI
 *  - [VerifyingEmailFragmentReducer] to determine transitions
 *
 * The states model the lifecycle of the email verification process:
 *
 *  1. Initial           — UI is loading/shimmering while email is fetched
 *  2. EmailFound        — Email loaded, user can request a verification email
 *  3. SendingEmail      — Sending email verification link
 *  4. VerifyingEmail    — Polling verification + countdown running
 *  5. EmailVerified     — Email successfully verified and UI shows confirmation
 */
sealed class VerifyingEmailFragmentState : State {

    /**
     * Initial loading state while the stored user email is being retrieved.
     */
    data object Initial : VerifyingEmailFragmentState()

    /**
     * The user email has been successfully retrieved and can now be displayed.
     * User interaction is enabled (send verification email).
     */
    data object EmailFound : VerifyingEmailFragmentState()

    /**
     * A verification email is being sent to the user.
     * UI disables buttons and may display a loading indicator.
     */
    data object SendingEmail : VerifyingEmailFragmentState()

    /**
     * The app is now waiting for the user to verify their email.
     * A countdown timer may be displayed, and background polling may be active.
     */
    data object VerifyingEmail : VerifyingEmailFragmentState()

    /**
     * The user's email has been successfully verified.
     * UI shows the confirmation animation and schedules navigation.
     */
    data object EmailVerified : VerifyingEmailFragmentState()

    // ---------------------------------------------------------------------------------------------
    // State Transition Guards
    // ---------------------------------------------------------------------------------------------
    // These helpers ensure that state transitions can only occur from valid previous states.
    // If a transition is attempted from an invalid state, an exception is thrown,
    // protecting the state machine from incorrect or unintended flows.
    // ---------------------------------------------------------------------------------------------
    /**
     * Transitions the current state to [EmailFound].
     *
     * This transition is allowed when:
     *  - The flow is starting and no email has been displayed yet [Initial].
     *  - The verification flow is restarting after a timeout or cancellation [VerifyingEmail] → [EmailFound].
     *
     * Any attempt to transition to [EmailFound] from another state indicates
     * an invalid state flow and will throw an exception.
     *
     * @throws IllegalStateException if invoked from a state other than [Initial]
     *         or [VerifyingEmail].
     */
    fun emailFound(): EmailFound {
        if (this !is Initial && this !is VerifyingEmail) throw IllegalStateException(
            "emailFound() can only be called from Initial or VerifyingEmail state."
        )
        return EmailFound
    }

    /**
     * Transition from [EmailFound] → [SendingEmail].
     */
    fun sendingEmail(): VerifyingEmailFragmentState {
        if (this !is EmailFound) throw IllegalStateException(
            "sendingEmail() can only be called from EmailFound state."
        )
        return SendingEmail
    }

    /**
     * Transition from [SendingEmail] → [VerifyingEmail].
     */
    fun verifyingEmail(): VerifyingEmailFragmentState {
        if (this !is SendingEmail) throw IllegalStateException(
            "verifyingEmail() can only be called from SendingEmail state."
        )
        return VerifyingEmail
    }

    /**
     * Transition from either:
     *   - [SendingEmail] → [EmailVerified]
     *   - [VerifyingEmail] → [EmailVerified]
     *
     * This flexibility allows the ViewModel to short-circuit the verification process
     * if the email is already verified when attempting to send the link.
     */
    fun emailVerified(): VerifyingEmailFragmentState {
        if (this !is SendingEmail && this !is VerifyingEmail) throw IllegalStateException(
            "emailVerified() can only be called from SendingEmail or VerifyingEmail states."
        )
        return EmailVerified
    }

}