package com.example.truckercore.view_model.view_models.continue_register.effect

import com.example.truckercore.view_model._shared._base.managers.EffectManager

class ContinueRegisterEffectManager : EffectManager<ContinueRegisterEffect>() {

    fun setShowErrorMessageEffect(message: String) {
        trySend(ContinueRegisterEffect.ShowErrorMessage(message))
    }

    fun setNavigateToVerifyEmailEffect() {
        trySend(ContinueRegisterEffect.Navigation.ToVerifyEmail)
    }

    fun setNavigateToUserNameEffect() {
        trySend(ContinueRegisterEffect.Navigation.ToUserName)
    }

    fun setNavigateToEmailAuthEffect() {
        trySend(ContinueRegisterEffect.Navigation.ToEmailAuth)
    }

    fun setNavigateToNotificationEffect() {
        trySend(ContinueRegisterEffect.Navigation.ToNotification)
    }

    fun setNavigateToLoginEffect() {
        trySend(ContinueRegisterEffect.Navigation.ToLogin)
    }

}