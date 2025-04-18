package com.example.truckercore.view_model.view_models.verifying_email

import com.example.truckercore.model.infrastructure.integration.exceptions.ErrorCode
import com.example.truckercore.view.fragments.verifying_email.VerifyingEmailFragment

/**
 * Represents one-time effects (side effects) emitted by [VerifyingEmailViewModel] and consumed
 * by [VerifyingEmailFragment] to trigger UI actions that should occur only once.
 *
 * These effects typically represent things like navigation, showing messages, or handling errors.
 */
sealed class VerifyingEmailEffect {

    /**
     * Triggered when the resend countdown reaches zero.
     * Used to update the UI to enable the "resend email" button.
     */
    data object CounterReachZero : VerifyingEmailEffect()

    /**
     * Triggered when the email verification process completes successfully.
     * Typically leads to navigation to the home screen.
     */
    data object EmailVerificationSucceed : VerifyingEmailEffect()

    /**
     * Triggered when email verification fails, containing the specific [errorCode].
     * Used to show an error message or navigate to an error screen.
     */
    data class EmailVerificationFailed(val errorCode: ErrorCode) : VerifyingEmailEffect()

    /**
     * Triggered when the resend verification email is sent successfully.
     * Contains a [message] to be shown to the user (e.g., a toast).
     */
    data class SendEmailSucceed(val message: String) : VerifyingEmailEffect()

    /**
     * Triggered when sending the verification email fails.
     * Contains the related [errorCode] for appropriate UI feedback.
     */
    data class SendEmailFailed(val errorCode: ErrorCode) : VerifyingEmailEffect()

}