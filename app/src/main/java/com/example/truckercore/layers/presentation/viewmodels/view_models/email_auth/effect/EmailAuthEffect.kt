package com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.effect

import com.example.truckercore.layers.presentation.viewmodels.base._contracts.Effect

sealed class EmailAuthEffect : Effect {

    sealed class Navigation : EmailAuthEffect() {
        data object ToNotification : Navigation()
        data object ToLogin : Navigation()
        data object ToVerifyEmail : Navigation()
        data object ToNoConnection: Navigation()
    }

    sealed class View : EmailAuthEffect() {
        data object ClearFocusAndKeyboard : View()
        data class WarningToast(val message: String) : View()
    }

}