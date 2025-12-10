package com.example.truckercore.layers.presentation.nav_login.view_model.login.helpers

import com.example.truckercore.core.my_lib.classes.EmailCredential
import com.example.truckercore.layers.presentation.base.contracts.Effect

sealed class LoginFragmentEffect: Effect {

    data class LaunchLoginTask(val credential: EmailCredential): LoginFragmentEffect()

    sealed class Navigation: LoginFragmentEffect() {

        data object ToNotification: Navigation()

        data object ToNoConnection: Navigation()

        data object ToCheckIn: Navigation()

        data object ToForgetPassword: Navigation()

        data object ToNewUser: Navigation()

    }

}