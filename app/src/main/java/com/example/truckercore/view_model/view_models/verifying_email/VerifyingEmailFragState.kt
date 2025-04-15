package com.example.truckercore.view_model.view_models.verifying_email

import com.example.truckercore.view.fragments.verifying_email.VerifyingEmailFragment

/**
 * Represents the UI states for the [VerifyingEmailFragment].
 *
 * These states are used to determine how the screen should behave and what actions
 * are currently available to the user.
 */
sealed class VerifyingEmailFragState {

    /**
     * Indicates that the app is currently waiting for the user's email to be verified.
     * - The resend button is disabled
     * - A countdown timer is active
     */
    data object TryingToVerify : VerifyingEmailFragState()

    /**
     * Indicates that the countdown has ended and the user can now resend the verification email.
     * - The resend button is enabled
     */
    data object WaitingResend : VerifyingEmailFragState()

}