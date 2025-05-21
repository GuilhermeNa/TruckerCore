package com.example.truckercore.view_model.view_models.splash

import kotlinx.coroutines.flow.MutableStateFlow

class SplashUiStateManager {

    private var _uiState: MutableStateFlow<SplashUiState> = MutableStateFlow(SplashUiState.Initial)
    val uiState get() = _uiState

    fun setLoadingState() {
        _uiState.value = SplashUiState.Loading
    }



}
