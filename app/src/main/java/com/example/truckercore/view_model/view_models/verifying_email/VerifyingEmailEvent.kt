package com.example.truckercore.view_model.view_models.verifying_email

/**
 * Represents the different user actions or events that can occur during the email verification process.
 */
sealed class VerifyingEmailEvent {

    /**
     * Triggered when the user clicks the "Resend" button to request another verification email.
     */
    data object ResendButtonClicked : VerifyingEmailEvent()

    /**
     * Triggered when the countdown timer for resending the email reaches zero. This should enable resend button.
     */
    data object CounterReachZero : VerifyingEmailEvent()

    /**
     * Triggered when Firebase detects that the user has verified their email via the verification link.
     */
    data object EmailVerified: VerifyingEmailEvent()

}