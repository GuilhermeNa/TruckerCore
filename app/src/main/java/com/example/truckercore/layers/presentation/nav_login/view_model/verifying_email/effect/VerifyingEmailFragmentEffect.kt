package com.example.truckercore.layers.presentation.nav_login.view_model.verifying_email.effect

import com.example.truckercore.layers.presentation.base.contracts.Effect

sealed class VerifyingEmailFragmentEffect: Effect {

    sealed class Navigation: VerifyingEmailFragmentEffect() {

        data object ToLogin: Navigation()

        data object ToNotification: Navigation()

        data object ToNoConnection: Navigation()

    }

}