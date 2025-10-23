package com.example.truckercore.layers.presentation.viewmodels.view_models.user_name.effect

import com.example.truckercore.presentation.viewmodels._shared._base.managers.EffectManager

class UserNameEffectManager: com.example.truckercore.presentation.viewmodels._shared._base.managers.EffectManager<com.example.truckercore.presentation.viewmodels.view_models.user_name.effect.UserNameEffect>() {

    fun setShowMessageEffect(message: String) {
        trySend(
            com.example.truckercore.presentation.viewmodels.view_models.user_name.effect.UserNameEffect.ShowMessage(
                message
            )
        )
    }

    fun setNavigateToLoginEffect() {
        trySend(com.example.truckercore.presentation.viewmodels.view_models.user_name.effect.UserNameEffect.Navigation.ToLogin)
    }

    fun setNavigateToNotificationEffect() {
        trySend(com.example.truckercore.presentation.viewmodels.view_models.user_name.effect.UserNameEffect.Navigation.ToNotification)
    }

    fun setNavigateToMainEffect() {
        trySend(com.example.truckercore.presentation.viewmodels.view_models.user_name.effect.UserNameEffect.Navigation.ToMain)
    }

}