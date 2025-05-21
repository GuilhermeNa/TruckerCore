package com.example.truckercore.view_model.view_models.splash

import com.example.truckercore._utils.classes.contracts.Effect


sealed class SplashEffect: Effect {

    data object TransitionToLoading : SplashEffect()

    data object TransitionToNavigation : SplashEffect()

}