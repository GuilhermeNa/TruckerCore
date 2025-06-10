package com.example.truckercore.view_model.view_models.continue_register.effect

import com.example.truckercore.view_model._shared._contracts.Effect

sealed class ContinueRegisterEffect : Effect {

    data class ShowErrorMessage(val message: String) : ContinueRegisterEffect()

    data object NavigateToCreateNewEmail : ContinueRegisterEffect()

    data object NavigateToVerifyEmail : ContinueRegisterEffect()

    data object NavigateToRegisterName : ContinueRegisterEffect()

    data object NavigateToNotification: ContinueRegisterEffect()

}