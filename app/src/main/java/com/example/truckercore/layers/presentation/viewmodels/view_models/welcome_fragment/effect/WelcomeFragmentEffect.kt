package com.example.truckercore.layers.presentation.viewmodels.view_models.welcome_fragment.effect

import com.example.truckercore.layers.presentation.viewmodels.base._contracts.Effect

sealed class WelcomeFragmentEffect : Effect {

    sealed class Pagination : WelcomeFragmentEffect() {
        data object Back : Navigation()
        data object Forward : Navigation()
    }

    sealed class Navigation : WelcomeFragmentEffect() {
        data object ToNotification : Navigation()
        data object ToEmailAuth : Navigation()
    }

}