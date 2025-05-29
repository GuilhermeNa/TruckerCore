package com.example.truckercore.business_driver.view_model.view_models.splash_driver

import com.example.truckercore._utils.classes.abstractions.EffectManager

class SplashDriverEffectManager: EffectManager<SplashDriverEffect>() {

    fun setNavigateToWelcomeEffect() {
        trySend(SplashDriverEffect.NavigateToWelcome)
    }

    fun setNavigateToLoginEffect() {
        trySend(SplashDriverEffect.NavigateToLogin)
    }

    fun setNavigateToNotificationEffect() {
        trySend(SplashDriverEffect.NavigateToNotification)
    }

}