package com.example.truckercore.view_model.view_models.splash.event

import com.example.truckercore.view_model._shared._contracts.Event
import com.example.truckercore.view_model.view_models.splash.use_case.SplashDirection

sealed class SplashEvent : Event {

    sealed class TransitionEnd : SplashEvent() {
        data object ToLoading : TransitionEnd() {
            override fun toString() = "TransitionEnd -> ToLoading"
        }

        data class ToLoaded(val direction: SplashDirection) : TransitionEnd() {
            override fun toString() = "TransitionEnd -> ToLoaded(direction=$direction)"
        }
    }

    sealed class SystemEvent : SplashEvent() {
        data class Initialize(val appName: String) : SystemEvent() {
            override fun toString() = "SystemEvent -> Initialize(appName=\"$appName\")"
        }

        sealed class LoadUserTask : SystemEvent() {
            data object Success : LoadUserTask() {
                override fun toString() = "LoadUserTask -> Success"
            }

            data object CriticalError : LoadUserTask() {
                override fun toString() = "LoadUserTask -> CriticalError"
            }
        }
    }

}