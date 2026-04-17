package com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.view_model

import com.example.truckercore.layers.presentation.base.contracts.State

sealed class EditBusinessStateNew: State {

    data object Loading : EditBusinessStateNew()

    data object Failure : EditBusinessStateNew()

    sealed class Loaded: EditBusinessStateNew() {
        data object Waiting: Loaded()
        data object Ready: Loaded()
    }

    fun failure() = Failure

    fun waitingInput() = Loaded.Waiting

    fun readyToSave() = Loaded.Ready

}