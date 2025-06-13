package com.example.truckercore.view_model.view_models.user_name.effect

import com.example.truckercore.view_model._shared._base.managers.EffectManager
import com.example.truckercore.view_model._shared.helpers.ViewError

class UserNameEffectManager: EffectManager<UserNameEffect>() {

    fun setShowMessageEffect(message: String) {
        trySend(UserNameEffect.ShowMessage(message))
    }

    fun setNavigateToLoginEffect() {
        trySend(UserNameEffect.Navigation.ToLogin)
    }

    fun setNavigateToNotificationEffect() {
        trySend(UserNameEffect.Navigation.ToNotification)
    }

    fun setNavigateToMainEffect() {
        trySend(UserNameEffect.Navigation.ToMain)
    }

}