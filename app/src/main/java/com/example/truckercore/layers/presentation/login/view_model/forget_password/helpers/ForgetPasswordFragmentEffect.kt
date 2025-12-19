package com.example.truckercore.layers.presentation.login.view_model.forget_password.helpers

import com.example.truckercore.layers.presentation.base.contracts.Effect
import com.example.truckercore.layers.presentation.login.view.fragments.forget_password.ForgetPasswordFragment
import com.example.truckercore.layers.presentation.login.view_model.forget_password.ForgetPasswordViewModel

/**
 * Represents the one-time side effects (navigation or notifications)
 * that the [ForgetPasswordFragment] should perform.
 *
 * These effects are emitted by [ForgetPasswordViewModel] and collected
 * by the fragment. Unlike states, effects are meant for single-use
 * actions that should not be persisted across configuration changes.
 */
sealed class ForgetPasswordFragmentEffect : Effect {

    /**
     * Triggered when the password reset is successful.
     *
     * The fragment should navigate back to the previous screen
     * and show a success message to the user.
     */
    data object NavigateBackAndNotifySuccess : ForgetPasswordFragmentEffect()

    /**
     * Triggered when there is a network or connection error
     * during the password reset operation.
     *
     * The fragment should navigate to a "No Connection" screen
     * or show a network error dialog.
     */
    data object NavigateToNoConnection : ForgetPasswordFragmentEffect()

    /**
     * Triggered when an irrecoverable error occurs during
     * the password reset operation.
     *
     * The fragment should navigate to a generic notification
     * or error screen to inform the user.
     */
    data object NavigateToNotification : ForgetPasswordFragmentEffect()

}