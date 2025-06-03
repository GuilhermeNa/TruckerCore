package com.example.truckercore.view_model.view_models.forget_password.effect

import com.example.truckercore.view_model._shared._base.managers.EffectManager

class ForgetPasswordEffectManager : EffectManager<ForgetPasswordEffect>() {

    fun setShowToastEffect(message: String) {
        trySend(ForgetPasswordEffect.ShowMessage(message))
    }

    fun setNavigateBackStackEffect() {
        trySend(ForgetPasswordEffect.Navigate.BackStack)
    }

    fun setNavigateToNotificationEffect() {
        trySend(ForgetPasswordEffect.Navigate.ToNotification)
    }

    fun setClearKeyboardAndFocusEffect() {
        trySend(ForgetPasswordEffect.ClearKeyboardAndFocus)
    }

}