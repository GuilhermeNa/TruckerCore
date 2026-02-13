package com.example.truckercore.business_admin.layers.presentation.check_in.view_model.helpers

import com.example.truckercore.layers.presentation.base.contracts.State

sealed class CheckInState: State {

    data object Loading: CheckInState()

    data object Idle: CheckInState()

    //----------------------------------------------------------------------------------------------
    //
    //----------------------------------------------------------------------------------------------
    fun idle() = Idle

    fun loading() = Loading

}