package com.example.truckercore.layers.presentation.login.view_model.welcome_fragment.helpers

import com.example.truckercore.layers.presentation.base.contracts.Event

/**
 * Represents user-driven events (inputs or actions) that occur
 * within the WelcomeFragment UI.
 */
sealed class WelcomeFragmentEvent : Event {

    /**
     * Represents click-based user actions.
     *
     * Each of these events is triggered when the user taps
     * one of the interactive buttons (such as FABs or skip/jump buttons).
     * The ViewModel receives these events and determines
     * whether to update the pager state or trigger a navigation effect.
     */
    sealed class Click : WelcomeFragmentEvent() {

        /**
         * Triggered when the user taps the **left FAB**.
         * Typically used to paginate **backward** in the ViewPager.
         */
        data object LeftFab : Click()

        /**
         * Triggered when the user taps the **right FAB**.
         * Typically used to paginate **forward** in the ViewPager or **navigate** to next fragment.
         */
        data object RightFab : Click()

        /**
         * Triggered when the user taps the **"Jump" or "Skip" button**.
         * Usually navigates the user out of the onboarding flow
         * (for example, to the authentication or home screen).
         */
        data object SkipButton : Click()

    }

    /**
     * Triggered when the user **swipes** the pager manually.
     *
     * @property position The new position (page index) after the swipe.
     * Used to update the ViewModel state to match the current page.
     */
    data class PagerSwiped(val position: Int) : WelcomeFragmentEvent()

}