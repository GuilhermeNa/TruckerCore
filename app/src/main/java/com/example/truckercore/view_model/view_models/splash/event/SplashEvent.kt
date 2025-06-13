package com.example.truckercore.view_model.view_models.splash.event

import com.example.truckercore.view_model._shared._contracts.Event

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
        data object TransitionToLoadingComplete : UiEvent()
        data object TransitionToNavigationComplete : UiEvent()
    }

    /**
     * Represents system-related events triggered based on the loading or state of the system.
     */
    sealed class SystemEvent : SplashEvent() {
        sealed class LoadUserTask: SystemEvent() {
            data object Execute: LoadUserTask()
            data object Success: LoadUserTask()
            data object CriticalError: LoadUserTask()
        }
    }

}