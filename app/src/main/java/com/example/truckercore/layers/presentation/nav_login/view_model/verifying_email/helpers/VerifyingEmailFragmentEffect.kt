package com.example.truckercore.layers.presentation.nav_login.view_model.verifying_email.helpers

import com.example.truckercore.layers.presentation.base.contracts.Effect
import com.example.truckercore.layers.presentation.base.managers.EffectManager
import kotlinx.coroutines.flow.SharedFlow

/**
 * Represents one-shot, non-persistent actions that the ViewModel sends to the Fragment.
 *
 * Effects differ from state because:
 *  - They are consumed only once
 *  - They are not preserved through configuration changes
 *  - They trigger behaviors, not UI state representation
 *
 * Examples:
 *  - Starting asynchronous tasks (send email, verify email)
 *  - Triggering navigation
 *  - Cancelling background jobs
 *
 * The Fragment observes these via a cold [SharedFlow] provided by [EffectManager].
 */
sealed class VerifyingEmailFragmentEffect : Effect {

    // ---------------------------------------------------------------------------------------------
    // Effects Related to Job Control
    // ---------------------------------------------------------------------------------------------
    /**
     * Instructs the ViewModel to send a verification email.
     * This effect is triggered by the reducer after the user clicks the send button
     * or during retry logic.
     */
    data object LaunchSendEmailTask : VerifyingEmailFragmentEffect()

    /**
     * Requests the ViewModel to begin the verification process:
     *  - Start polling the backend
     *  - Start the countdown timer
     */
    data object LaunchVerifyEmailTask : VerifyingEmailFragmentEffect()

    /**
     * Requests cancellation of any running verification-related tasks
     * (countdown timer, polling job).
     */
    data object CancelVerifyEmailTask : VerifyingEmailFragmentEffect()

    // ---------------------------------------------------------------------------------------------
    // Navigation Effects
    // ---------------------------------------------------------------------------------------------
    // These effects instruct the Fragment to navigate to a new screen.
    // They are explicitly separated because navigation is always a one-shot action.
    // ---------------------------------------------------------------------------------------------
    sealed class Navigation : VerifyingEmailFragmentEffect() {
        data object ToProfile : Navigation()
        data object ToNewEmail : Navigation()
        data object ToLogin : Navigation()
        data object ToNoConnection : Navigation()
        data object ToNotification : Navigation()
    }

    // ---------------------------------------------------------------------------------------------
    // Effect Type Identifiers
    // ---------------------------------------------------------------------------------------------
    val isLaunchSendEmailTask get() = this is LaunchSendEmailTask

    val isLaunchVerifyTask get() = this is LaunchVerifyEmailTask

    val isNavigation get() = this is Navigation

    val isCancelVerifyTask get() = this is CancelVerifyEmailTask

}