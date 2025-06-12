package com.example.truckercore.view_model.view_models.user_name.effect

import com.example.truckercore.view_model._shared._contracts.Effect

sealed class UserNameEffect : Effect {

    sealed class Navigation: UserNameEffect() {
        data object ToMain : Navigation()
        data object ToNotification : Navigation()
    }

    data class ShowMessage(val message: String) : UserNameEffect()

}