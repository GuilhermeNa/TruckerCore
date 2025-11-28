package com.example.truckercore.layers.presentation.nav_login.view_model.verifying_email.effect

import com.example.truckercore.layers.presentation.base.contracts.Effect

sealed class VerifyingEmailFragmentEffect: Effect {

    val isLaunchSendEmailTask get() = this is LaunchSendEmailTask

    val isLaunchVerifyTask get() = this is LaunchVerifyEmailTask

    val isNavigation get() = this is Navigation

    val isCancelVerifyTask get() = this is Navigation

    data object CancelVerifyEmailTask: VerifyingEmailFragmentEffect()

    data object LaunchSendEmailTask: VerifyingEmailFragmentEffect()

    data object LaunchVerifyEmailTask: VerifyingEmailFragmentEffect()

    sealed class Navigation: VerifyingEmailFragmentEffect() {

        data object ToProfile: Navigation()

        data object ToNewEmail: Navigation()

        data object ToLogin: Navigation()

        data object ToNoConnection: Navigation()

        data object ToNotification: Navigation()

    }

}