package com.example.truckercore.view_model.view_models.splash.effect

import com.example.truckercore.view_model._shared._base.managers.EffectManager

class SplashEffectManager : EffectManager<SplashEffect>() {

    fun setTransitionToLoading() {
        trySend(SplashEffect.Transition.ToLoading)
    }

    fun setTransitionToLoaded() {
        trySend(SplashEffect.Transition.ToLoaded)
    }

    fun setNavigateToNotificationEffect() {
        trySend(SplashEffect.Navigate.ToNotification)
    }

    fun setNavigateToLoginEffect() {
        trySend(SplashEffect.Navigate.ToLogin)
    }

    fun setNavigateToWelcomeEffect() {
        trySend(SplashEffect.Navigate.ToWelcome)
    }

    fun setNavigateToContinueEffect() {
        trySend(SplashEffect.Navigate.ToContinue)
    }

    fun setNavigateToMainEffect() {
        trySend(SplashEffect.Navigate.ToMain)
    }

}