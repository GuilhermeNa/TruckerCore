package com.example.truckercore.layers.presentation.login.view_model.splash.helpers

import com.example.truckercore.layers.presentation.base.contracts.Event

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