package com.example.truckercore.view_model.view_models.continue_register.effect

import com.example.truckercore.view_model._shared._contracts.Effect

sealed class ContinueRegisterEffect : Effect {

    data class ShowErrorMessage(val message: String) : ContinueRegisterEffect()

    sealed class Navigation : ContinueRegisterEffect() {

        data object ToEmailAuth : Navigation()

        data object ToVerifyEmail : Navigation()

        data object ToUserName : Navigation()

        data object ToNotification : Navigation()

        data object ToLogin : Navigation()
    }

}