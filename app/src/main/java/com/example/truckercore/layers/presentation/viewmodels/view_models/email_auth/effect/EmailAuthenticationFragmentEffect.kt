package com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.effect

import com.example.truckercore.layers.presentation.viewmodels.base._contracts.Effect

sealed class EmailAuthenticationFragmentEffect : Effect {

    sealed class Navigation : EmailAuthenticationFragmentEffect() {
        data object ToNotification : Navigation()
        data object ToLogin : Navigation()
        data object ToVerifyEmail : Navigation()
        data object ToNoConnection : Navigation()
    }

    sealed class Task : EmailAuthenticationFragmentEffect() {
        data object AuthenticateEmail : Task()
    }

    data class WarningToast(val message: String) : EmailAuthenticationFragmentEffect()

}