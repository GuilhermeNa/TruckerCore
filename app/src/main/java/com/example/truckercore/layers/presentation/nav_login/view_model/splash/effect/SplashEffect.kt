package com.example.truckercore.layers.presentation.nav_login.view_model.splash.effect

import com.example.truckercore.layers.presentation.base.contracts.Effect

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

        sealed class Navigation : UiEffect() {
            data object ToWelcome : Navigation() {
                override fun toString() = "Navigate -> ToWelcome"
            }

            data object ToContinue : Navigation() {
                override fun toString() = "Navigate -> ToContinue"
            }

            data object ToLogin : Navigation() {
                override fun toString() = "Navigate -> ToLogin"
            }

            data object ToMain : Navigation() {
                override fun toString() = "Navigate -> ToMain"
            }

            data object ToNotification : Navigation() {
                override fun toString() = "Navigate -> ToNotification"
            }
        }
    }

    sealed class SystemEffect : SplashEffect() {
        data object ExecuteLoadUserTask : SystemEffect()
    }

}