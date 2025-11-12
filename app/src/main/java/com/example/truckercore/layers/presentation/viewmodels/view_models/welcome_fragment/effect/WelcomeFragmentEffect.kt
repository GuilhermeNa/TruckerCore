package com.example.truckercore.layers.presentation.viewmodels.view_models.welcome_fragment.effect

import com.example.truckercore.layers.presentation.viewmodels.base._contracts.Effect

/**
 * Represents one-time side effects that can occur
 * in the WelcomeFragment, such as navigation actions or pagination changes.
 */
sealed class WelcomeFragmentEffect : Effect {

    /**
     * Represents effects related to pagination actions within the welcome ViewPager.
     *
     * These effects are triggered when the user interacts with the
     * Floating Action Buttons (FABs) — for example, tapping the "Next" or "Back" button.
     */
    sealed class Pagination : WelcomeFragmentEffect() {

        /**
         * Triggered when the user taps the FAB to go back to the previous page.
         */
        data object Back : Pagination()

        /**
         * Triggered when the user taps the FAB to advance to the next page.
         */
        data object Forward : Pagination()

    }

    /**
     * Represents effects related to navigation outside of the pager —
     * for example, moving from the welcome flow to another part of the app.
     */
    sealed class Navigation : WelcomeFragmentEffect() {

        /**
         * Effect to navigate to the notification activity when any error occur.
         */
        data object ToNotification : Navigation()

        /**
         * Effect to navigate to the email authentication activity.
         */
        data object ToEmailAuth : Navigation()

    }

}