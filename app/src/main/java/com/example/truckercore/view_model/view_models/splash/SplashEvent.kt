package com.example.truckercore.view_model.view_models.splash

sealed class SplashEvent {

    sealed class UiEvent: SplashEvent() {
        data object FirstAnimComplete: UiEvent()
        data object SecondAnimComplete: UiEvent()
    }

    sealed class SystemEvent: SplashEvent() {
        data object UserInFirstAccess: SystemEvent()
        data object LoginRequired: SystemEvent()
        data object EnterSystem: SystemEvent()
        data object FinishRegistration: SystemEvent()
    }

}