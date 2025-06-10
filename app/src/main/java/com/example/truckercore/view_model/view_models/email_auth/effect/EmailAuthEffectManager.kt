package com.example.truckercore.view_model.view_models.email_auth.effect

import com.example.truckercore.view_model._shared._base.managers.EffectManager

class EmailAuthEffectManager : EffectManager<EmailAuthEffect>() {

    fun setClearFocusAndHideKeyboardEffect() {
        trySend(EmailAuthEffect.ClearFocusAndHideKeyboard)
    }

    fun setNavigateToLoginEffect() {
        trySend(EmailAuthEffect.NavigateToLogin)
    }

    fun setNavigateToVerifyEmailEffect() {
        trySend(EmailAuthEffect.NavigateToVerifyEmail)
    }

    fun setShowToastEffect(message: String) {
        trySend(EmailAuthEffect.ShowToast(message))
    }

    fun setNavigateToNotificationEffect() {
        trySend(EmailAuthEffect.NavigateToNotification)
    }

}