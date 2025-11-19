package com.example.truckercore.layers.presentation.nav_login.view_model.login.effect

import com.example.truckercore.presentation.viewmodels._shared._base.managers.EffectManager

class LoginEffectManager : com.example.truckercore.presentation.viewmodels._shared._base.managers.EffectManager<com.example.truckercore.presentation.viewmodels.view_models.login.effect.LoginEffect>() {

    fun setCLearFocusAndHideKeyboardEffect() {
        trySend(com.example.truckercore.presentation.viewmodels.view_models.login.effect.LoginEffect.ClearFocusAndHideKeyboard)
    }

    fun setShowToastEffect(message: String) {
        trySend(
            com.example.truckercore.presentation.viewmodels.view_models.login.effect.LoginEffect.ShowToast(
                message
            )
        )
    }

    fun setNavigateToMainEffect() {
        trySend(com.example.truckercore.presentation.viewmodels.view_models.login.effect.LoginEffect.NavigateToMain)
    }

    fun setNavigateToNewUserEffect() {
        trySend(com.example.truckercore.presentation.viewmodels.view_models.login.effect.LoginEffect.NavigateToNewUser)
    }

    fun setNavigateToForgetPasswordEffect() {
        trySend(com.example.truckercore.presentation.viewmodels.view_models.login.effect.LoginEffect.NavigateToForgetPassword)
    }

    fun setNavigateToNotificationEffect() {
        trySend(com.example.truckercore.presentation.viewmodels.view_models.login.effect.LoginEffect.NavigateToNotification)
    }

}