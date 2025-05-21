package com.example.truckercore.view_model.view_models.splash


sealed class SplashEffect {

    data object TransitionToLoading : SplashEffect()

    data object TransitionToNavigation : SplashEffect()

}