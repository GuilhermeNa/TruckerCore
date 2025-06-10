package com.example.truckercore.view_model.view_models.continue_register.effect

import com.example.truckercore.view_model._shared._base.managers.EffectManager

class ContinueRegisterEffectManager: EffectManager<ContinueRegisterEffect>() {

    fun setShowErrorMessageEffect(message: String) {
        trySend(ContinueRegisterEffect.ShowErrorMessage(message))
    }

    fun setNavigateToVerifyEmailEffect() {
        trySend(ContinueRegisterEffect.NavigateToVerifyEmail)
    }

    fun setNavigateToRegisterNameEffect() {
        trySend(ContinueRegisterEffect.NavigateToRegisterName)
    }

    fun setNavigateToCreateEmailEffect() {
        trySend(ContinueRegisterEffect.NavigateToCreateNewEmail)
    }

    fun setNavigateToNotificationEffect() {
        trySend(ContinueRegisterEffect.NavigateToNotification)
    }

}