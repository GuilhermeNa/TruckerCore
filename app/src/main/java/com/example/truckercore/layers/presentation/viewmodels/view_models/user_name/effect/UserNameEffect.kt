package com.example.truckercore.layers.presentation.viewmodels.view_models.user_name.effect

import com.example.truckercore.domain._shared._contracts.Effect

sealed class UserNameEffect : Effect {

    sealed class Navigation: UserNameEffect() {
        data object ToLogin : Navigation()
        data object ToMain : Navigation()
        data object ToNotification : Navigation()
    }

    data class ShowMessage(val message: String) : UserNameEffect()

}