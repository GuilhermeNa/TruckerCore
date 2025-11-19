package com.example.truckercore.layers.presentation.nav_login.view_model.email_auth.effect

import com.example.truckercore.layers.presentation.common.NoConnectionFragment
import com.example.truckercore.layers.presentation.common.NotificationActivity
import com.example.truckercore.layers.presentation.nav_login.fragments.email_auth.EmailAuthFragment
import com.example.truckercore.layers.presentation.nav_login.fragments.login.LoginFragment
import com.example.truckercore.layers.presentation.nav_login.view.fragments.verifying_email.VerifyingEmailFragment
import com.example.truckercore.layers.presentation.base.contracts.Effect

/**
 * Represents side-effects in the [EmailAuthFragment] screen.
 *
 * Effects are one-time actions that the View should perform in response
 * to a state change or event. Unlike state, effects are **not retained**.
 */
sealed class EmailAuthenticationFragmentEffect : Effect {

    /** Navigation effects to move the user between screens. */
    sealed class Navigation : EmailAuthenticationFragmentEffect() {

        /** Navigate to the [NotificationActivity]. */
        data object ToNotification : Navigation()

        /** Navigate to the [LoginFragment]. */
        data object ToLogin : Navigation()

        /** Navigate to the [VerifyingEmailFragment]. */
        data object ToVerifyEmail : Navigation()

        /** Navigate to the [NoConnectionFragment] */
        data object ToNoConnection : Navigation()

    }

    /** Task effects that trigger specific actions. */
    sealed class Task : EmailAuthenticationFragmentEffect() {

        /** Trigger the email authentication task. */
        data object AuthenticateEmail : Task()

    }

    /** Show a transient warning toast to the user. */
    data class WarningToast(val message: String) : EmailAuthenticationFragmentEffect()

}