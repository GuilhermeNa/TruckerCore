package com.example.truckercore.business_admin.layers.presentation.main.fragments.home.view_model

import com.example.truckercore.layers.presentation.base.abstractions.view_model.BaseViewModel
import com.example.truckercore.layers.presentation.base.managers.StateManager

/**
 * HomeViewModel
 *
 * Manages HomeState and exposes it via StateFlow.
 *
 * Follows a unidirectional state update approach:
 * - State is immutable.
 * - Updates create new copies.
 * - UI reacts to state changes.
 */
class HomeViewModel : BaseViewModel() {

    private val stateManager = StateManager(HomeState.create())

    /**
     * Public observable state.
     */
    val stateFlow = stateManager.stateFlow

    /**
     * Updates interaction state.
     */
    fun setInteraction(enabled: Boolean) {
        val newState = stateFlow.value.setInteraction(enabled)
        stateManager.update(newState)
    }

}