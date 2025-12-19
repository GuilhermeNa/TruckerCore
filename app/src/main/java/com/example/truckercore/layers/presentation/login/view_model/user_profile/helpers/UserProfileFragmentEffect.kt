package com.example.truckercore.layers.presentation.login.view_model.user_profile.helpers

import com.example.truckercore.layers.presentation.base.contracts.Effect

/**
 * Represents one-time events (effects) emitted by the ViewModel that should be
 * acted upon by the fragment. Unlike state, effects are not persistent and are
 * consumed only once.
 *
 * These effects typically instruct the UI to:
 * - Navigate to another screen,
 * - Execute a one-time operation such as starting a task.
 */
sealed class UserProfileFragmentEffect : Effect {

    /**
     * Defines all navigation-related effects that the user profile screen
     * may trigger. These effects correspond to one-time instructions for the
     * fragment to move to a specific destination.
     */
    sealed class Navigation : UserProfileFragmentEffect() {

        /**
         * Navigates the user to the login screen when authentication is required.
         */
        data object ToLogin : Navigation()

        /**
         * Navigates the user to the check-in screen after successful profile creation.
         */
        data object ToCheckIn : Navigation()

        /**
         * Navigates to a generic notification or error screen in case of an
         * unrecoverable failure.
         */
        data object ToNotification : Navigation()

        /**
         * Navigates to a dedicated no-connection screen, typically following
         * a recoverable network-related failure.
         */
        data object ToNoConnection : Navigation()
    }

    /**
     * Triggers the user profile creation task. This effect is consumed by the
     * ViewModel and does not update UI state by itself.
     */
    data object ProfileTask : UserProfileFragmentEffect()

}