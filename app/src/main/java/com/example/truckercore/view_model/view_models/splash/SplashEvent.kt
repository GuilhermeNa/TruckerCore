package com.example.truckercore.view_model.view_models.splash

import com.example.truckercore._utils.classes.contracts.Event
import com.example.truckercore.view.ui_error.UiError

/**
 * Represents events triggered by the splash screen's UI or system.
 *
 * These events are used to communicate state changes or actions between
 * the ViewModel and the UI (Splash screen).
 */
sealed class SplashEvent: Event {

    /**
     * Represents UI-related events that are triggered based on user interactions or animations.
     */
    sealed class UiEvent : SplashEvent() {
        data object Initialized: UiEvent()
        /**
         * Indicates that the first animation has completed.
         * This event should be triggered once the first animation finishes.
         */
        data object TransitionToLoadingComplete : UiEvent()

        /**
         * Indicates that the second animation has completed.
         * This event should be triggered once the second animation finishes.
         */
        data object TransitionToNavigationComplete : UiEvent()
    }

    /**
     * Represents system-related events triggered based on the loading or state of the system.
     */
    sealed class SystemEvent : SplashEvent() {
        /**
         * Indicates that user information has been loaded and is ready for use.
         * This event may be used to transition to another screen or update UI.
         */
        data class ReceivedApiSuccess(val direction: SplashUiState.Navigating) : SystemEvent()

        data object Error: SystemEvent()

    }

}