package com.example.truckercore.view_model.view_models.forget_password.effect

import com.example.truckercore._utils.classes.contracts.Effect

sealed class ForgetPasswordEffect : Effect {

    sealed class Navigate : ForgetPasswordEffect() {
        data object BackStack : ForgetPasswordEffect()
        data object ToNotification : ForgetPasswordEffect()
    }

    data class ShowMessage(val message: String) : ForgetPasswordEffect()

    data object ClearKeyboardAndFocus : ForgetPasswordEffect()

}