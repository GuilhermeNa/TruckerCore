package com.example.truckercore.view_model.view_models.splash.effect

import com.example.truckercore.view_model._shared._contracts.Effect


sealed class SplashEffect : Effect {

    sealed class Transition : SplashEffect() {
        data object ToLoading : Transition()
        data object ToLoaded : Transition()
    }

    sealed class Navigate : SplashEffect() {
        data object ToMain : Navigate()
        data object ToLogin : Navigate()
        data object ToWelcome : Navigate()
        data object ToContinue : Navigate()
        data object ToNotification : Navigate()
    }

}