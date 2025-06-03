package com.example.truckercore.view_model.view_models.splash

import com.example.truckercore.view_model._shared._contracts.Effect


sealed class SplashEffect: Effect {

    data object TransitionToLoading : SplashEffect()

    data object TransitionToNavigation : SplashEffect()

}