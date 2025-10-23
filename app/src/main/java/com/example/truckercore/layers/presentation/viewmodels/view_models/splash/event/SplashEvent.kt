package com.example.truckercore.layers.presentation.viewmodels.view_models.splash.event

import com.example.truckercore.domain.view_models.splash.use_case.SplashDirection

sealed class SplashEvent :
    com.example.truckercore.presentation.viewmodels._shared._contracts.Event {

    sealed class TransitionEnd : com.example.truckercore.layers.presentation.viewmodels.view_models.splash.event.SplashEvent() {
        data object ToLoading : com.example.truckercore.layers.presentation.viewmodels.view_models.splash.event.SplashEvent.TransitionEnd() {
            override fun toString() = "TransitionEnd -> ToLoading"
        }

        data class ToLoaded(val direction: SplashDirection) : _root_ide_package_.com.example.truckercore.layers.presentation.viewmodels.view_models.splash.event.SplashEvent.TransitionEnd() {
            override fun toString() = "TransitionEnd -> ToLoaded(direction=$direction)"
        }
    }

    sealed class SystemEvent : _root_ide_package_.com.example.truckercore.layers.presentation.viewmodels.view_models.splash.event.SplashEvent() {
        data class Initialize(val appName: String) : _root_ide_package_.com.example.truckercore.layers.presentation.viewmodels.view_models.splash.event.SplashEvent.SystemEvent() {
            override fun toString() = "SystemEvent -> Initialize(appName=\"$appName\")"
        }

        sealed class LoadUserTask : _root_ide_package_.com.example.truckercore.layers.presentation.viewmodels.view_models.splash.event.SplashEvent.SystemEvent() {
            data object Success : _root_ide_package_.com.example.truckercore.layers.presentation.viewmodels.view_models.splash.event.SplashEvent.SystemEvent.LoadUserTask() {
                override fun toString() = "LoadUserTask -> Success"
            }

            data object CriticalError : _root_ide_package_.com.example.truckercore.layers.presentation.viewmodels.view_models.splash.event.SplashEvent.SystemEvent.LoadUserTask() {
                override fun toString() = "LoadUserTask -> CriticalError"
            }
        }
    }

}