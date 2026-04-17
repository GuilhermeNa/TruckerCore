package com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.view_model

import com.example.truckercore.layers.presentation.base.contracts.State

sealed class EditBusinessState: State {

    data object Loading : EditBusinessState()

    data object Failure : EditBusinessState()

    sealed class Loaded: EditBusinessState() {
        data object Waiting: Loaded()
        data object Ready: Loaded()
    }

    fun failure() = Failure

    fun waitingInput() = Loaded.Waiting

    fun readyToSave() = Loaded.Ready

}