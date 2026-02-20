package com.example.truckercore.business_admin.layers.presentation.main.fragments.home.view_model

import com.example.truckercore.layers.presentation.base.abstractions.view_model.BaseViewModel
import com.example.truckercore.layers.presentation.base.managers.StateManager

class HomeViewModel : BaseViewModel() {

    private val stateManager = StateManager(HomeState.create())
    val stateFlow = stateManager.stateFlow

    fun setInteraction(enabled: Boolean) {
        val newState = stateFlow.value.setInteraction(enabled)
        stateManager.update(newState)
    }

}