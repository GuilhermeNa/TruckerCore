package com.example.truckercore.business_driver.view_model.view_models.splash_driver

import com.example.truckercore._utils.classes.contracts.Effect

sealed class SplashDriverEffect: Effect {

    data object NavigateToWelcome: SplashDriverEffect()

    data object NavigateToLogin: SplashDriverEffect()

    data object NavigateToNotification: SplashDriverEffect()

}