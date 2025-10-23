package com.example.truckercore.layers.presentation.viewmodels.view_models.forget_password.effect

import com.example.truckercore.domain._shared._contracts.Effect

sealed class ForgetPasswordEffect : Effect {

    sealed class Navigate : com.example.truckercore.presentation.viewmodels.view_models.forget_password.effect.ForgetPasswordEffect() {
        data object BackStack : com.example.truckercore.presentation.viewmodels.view_models.forget_password.effect.ForgetPasswordEffect()
        data object ToNotification : com.example.truckercore.presentation.viewmodels.view_models.forget_password.effect.ForgetPasswordEffect()
    }

    data class ShowMessage(val message: String) : com.example.truckercore.presentation.viewmodels.view_models.forget_password.effect.ForgetPasswordEffect()

    data object ClearKeyboardAndFocus : com.example.truckercore.presentation.viewmodels.view_models.forget_password.effect.ForgetPasswordEffect()

}