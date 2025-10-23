package com.example.truckercore.layers.presentation.viewmodels.view_models.splash.effect

import com.example.truckercore.domain._shared._contracts.Effect

sealed class SplashEffect : Effect {

    sealed class UiEffect : SplashEffect() {

        sealed class Transition : UiEffect() {
            data object ToLoading : Transition() {
                override fun toString() = "Transition -> ToLoading"
            }

            data object ToLoaded : Transition() {
                override fun toString() = "Transition -> ToLoaded"
            }
        }

        sealed class Navigate : UiEffect() {
            data object ToWelcome : Navigate() {
                override fun toString() = "Navigate -> ToWelcome"
            }

            data object ToContinue : Navigate() {
                override fun toString() = "Navigate -> ToContinue"
            }

            data object ToLogin : Navigate() {
                override fun toString() = "Navigate -> ToLogin"
            }

            data object ToMain : Navigate() {
                override fun toString() = "Navigate -> ToMain"
            }

            data object ToNotification : Navigate() {
                override fun toString() = "Navigate -> ToNotification"
            }
        }
    }

    sealed class SystemEffect : SplashEffect() {
        data object ExecuteLoadUserTask : SystemEffect() {
            override fun toString() = "SystemEffect -> ExecuteLoadUserTask"
        }
    }

}