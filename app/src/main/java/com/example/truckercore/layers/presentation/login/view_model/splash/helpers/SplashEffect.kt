package com.example.truckercore.layers.presentation.login.view_model.splash.helpers

import com.example.truckercore.layers.presentation.base.contracts.Effect

sealed class SplashEffect : Effect {

    sealed class UiEffect : SplashEffect() {

        sealed class Transition : UiEffect() {
            data object ToLoading : Transition()

            data object ToLoaded : Transition()
        }

        sealed class Navigation : UiEffect() {
            data object ToWelcome : Navigation()

            data object ToContinue : Navigation()

            data object ToLogin : Navigation()

            data object ToCheckIn : Navigation()

            data object ToNotification : Navigation()
        }
    }

    sealed class SystemEffect : SplashEffect() {
        data object ExecuteLoadUserTask : SystemEffect()
    }

}