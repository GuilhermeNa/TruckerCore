package com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.effect

import com.example.truckercore.presentation.viewmodels._shared._base.managers.EffectManager

class EmailAuthEffectManager : com.example.truckercore.presentation.viewmodels._shared._base.managers.EffectManager<com.example.truckercore.presentation.viewmodels.view_models.email_auth.effect.EmailAuthEffect>() {

    fun setClearFocusAndHideKeyboardEffect() {
        trySend(com.example.truckercore.presentation.viewmodels.view_models.email_auth.effect.EmailAuthEffect.ClearFocusAndHideKeyboard)
    }

    fun setNavigateToLoginEffect() {
        trySend(com.example.truckercore.presentation.viewmodels.view_models.email_auth.effect.EmailAuthEffect.NavigateToLogin)
    }

    fun setNavigateToVerifyEmailEffect() {
        trySend(com.example.truckercore.presentation.viewmodels.view_models.email_auth.effect.EmailAuthEffect.NavigateToVerifyEmail)
    }

    fun setShowToastEffect(message: String) {
        trySend(
            com.example.truckercore.presentation.viewmodels.view_models.email_auth.effect.EmailAuthEffect.ShowToast(
                message
            )
        )
    }

    fun setNavigateToNotificationEffect() {
        trySend(com.example.truckercore.presentation.viewmodels.view_models.email_auth.effect.EmailAuthEffect.NavigateToNotification)
    }

}