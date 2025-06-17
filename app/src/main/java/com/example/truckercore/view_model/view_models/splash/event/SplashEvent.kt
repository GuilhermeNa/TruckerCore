package com.example.truckercore.view_model.view_models.splash.event

import com.example.truckercore.view_model._shared._contracts.Event
import com.example.truckercore.view_model.view_models.splash.use_case.SplashDirection

sealed class SplashEvent : Event {

    sealed class UiTransition : SplashEvent() {
        sealed class ToLoading : UiTransition() {
            data object Start : ToLoading()
            data object End : ToLoading()
        }

        sealed class ToLoaded : UiTransition() {
            data object Start : ToLoaded()
            data object End : ToLoaded()
        }
    }

    sealed class SystemEvent : SplashEvent() {
        data object Initialize : SystemEvent()

        sealed class LoadUserTask : SystemEvent() {
            data object Execute : LoadUserTask()
            data class Success(val direction: SplashDirection) : LoadUserTask()
            data object CriticalError : LoadUserTask()
        }
    }


}