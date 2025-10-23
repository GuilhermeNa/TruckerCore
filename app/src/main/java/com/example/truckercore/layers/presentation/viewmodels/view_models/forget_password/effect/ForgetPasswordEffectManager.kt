package com.example.truckercore.layers.presentation.viewmodels.view_models.forget_password.effect

import com.example.truckercore.presentation.viewmodels._shared._base.managers.EffectManager

class ForgetPasswordEffectManager : com.example.truckercore.presentation.viewmodels._shared._base.managers.EffectManager<com.example.truckercore.presentation.viewmodels.view_models.forget_password.effect.ForgetPasswordEffect>() {

    fun setShowToastEffect(message: String) {
        trySend(com.example.truckercore.presentation.viewmodels.view_models.forget_password.effect.ForgetPasswordEffect.ShowMessage(message))
    }

    fun setNavigateBackStackEffect() {
        trySend(com.example.truckercore.presentation.viewmodels.view_models.forget_password.effect.ForgetPasswordEffect.Navigate.BackStack)
    }

    fun setNavigateToNotificationEffect() {
        trySend(com.example.truckercore.presentation.viewmodels.view_models.forget_password.effect.ForgetPasswordEffect.Navigate.ToNotification)
    }

    fun setClearKeyboardAndFocusEffect() {
        trySend(com.example.truckercore.presentation.viewmodels.view_models.forget_password.effect.ForgetPasswordEffect.ClearKeyboardAndFocus)
    }

}