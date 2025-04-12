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
     * This typically means the user wants to restart the sign-up process with a different email or credentials.
     */
    data object NewAccountButtonClicked: VerifyingEmailEvent()

}