package com.example.truckercore.layers.presentation.viewmodels.view_models.splash.event

import com.example.truckercore.layers.presentation.viewmodels.base._contracts.Event
import com.example.truckercore.layers.presentation.viewmodels.view_models.splash.effect.SplashDirection

sealed class SplashEvent : Event {

    sealed class TransitionEnd : SplashEvent() {
        data object ToLoading : TransitionEnd()
        data class ToLoaded(val direction: SplashDirection) : TransitionEnd()
    }

    sealed class SystemEvent : SplashEvent() {
        data class Initialize(val appName: String) : SystemEvent()

        sealed class LoadUserTask : SystemEvent() {
            data object Success : LoadUserTask()
            data object Error : LoadUserTask()
        }
    }

}