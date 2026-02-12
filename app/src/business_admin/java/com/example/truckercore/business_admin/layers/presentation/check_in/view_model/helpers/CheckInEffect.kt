package com.example.truckercore.business_admin.layers.presentation.check_in.view_model.helpers

import com.example.truckercore.layers.presentation.base.contracts.Effect

sealed class CheckInEffect: Effect {

    data object LaunchCheckAccessTask: CheckInEffect()

    data object LaunchCreateAccessTask: CheckInEffect()

    sealed class Navigation: CheckInEffect() {
        data object ToMain: Navigation()
        data object ToNoConnection: Navigation()
        data object ToError: Navigation()
    }

}

