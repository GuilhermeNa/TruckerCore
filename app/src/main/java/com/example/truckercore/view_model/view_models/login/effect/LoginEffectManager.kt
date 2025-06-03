package com.example.truckercore.view_model.view_models.login.effect

import com.example.truckercore.view_model._shared._base.managers.EffectManager

class LoginEffectManager : EffectManager<LoginEffect>() {

    fun setCLearFocusAndHideKeyboardEffect() {
        trySend(LoginEffect.ClearFocusAndHideKeyboard)
    }

    fun setShowToastEffect(message: String) {
        trySend(LoginEffect.ShowToast(message))
    }

    fun setNavigateToMainEffect() {
        trySend(LoginEffect.NavigateToMain)
    }

    fun setNavigateToNewUserEffect() {
        trySend(LoginEffect.NavigateToNewUser)
    }

    fun setNavigateToForgetPasswordEffect() {
        trySend(LoginEffect.NavigateToForgetPassword)
    }

    fun setNavigateToNotificationEffect() {
        trySend(LoginEffect.NavigateToNotification)
    }

}