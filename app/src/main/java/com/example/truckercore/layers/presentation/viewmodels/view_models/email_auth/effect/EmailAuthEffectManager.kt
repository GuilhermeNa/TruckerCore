package com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.effect

import com.example.truckercore.layers.presentation.viewmodels.base._base.managers.EffectManager
import com.example.truckercore.layers.presentation.viewmodels.view_models.login.effect.LoginEffect

class EmailAuthEffectManager : EffectManager<EmailAuthenticationFragmentEffect>() {

    fun setClearFocusAndHideKeyboardEffect() {
        trySend(LoginEffect.ClearFocusAndHideKeyboard)
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