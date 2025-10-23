package com.example.truckercore.layers.presentation.viewmodels.view_models.continue_register.effect

import com.example.truckercore.presentation.viewmodels._shared._base.managers.EffectManager

class ContinueRegisterEffectManager : com.example.truckercore.presentation.viewmodels._shared._base.managers.EffectManager<ContinueRegisterEffect>() {

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