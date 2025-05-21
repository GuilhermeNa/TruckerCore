package com.example.truckercore.view_model.view_models.welcome_fragment

import kotlinx.coroutines.flow.MutableStateFlow

class WelcomeUiStateManager(pagerData: List<WelcomePagerData>) {

    private val _state: MutableStateFlow<WelcomeUiState> =
        MutableStateFlow(WelcomeUiState(data = pagerData))
    val state get() = _state

    fun setFirstPageState() {
        val newState = _state.value.copy(status = WelcomeUiState.Status.FirstPage)
        _state.value = newState
    }

}