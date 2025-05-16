package com.example.truckercore.view_model.view_models.preparing_ambient

import kotlinx.coroutines.flow.MutableStateFlow

class PreparingAmbientUiStateManager() {

    private val _state: MutableStateFlow<PreparingAmbientUiState> =
        MutableStateFlow(PreparingAmbientUiState.LoadingSession)
    val state get() = _state

    fun setSuccessState() {
        _state.value = PreparingAmbientUiState.Success
    }

    fun setErrorState() {
        _state.value = PreparingAmbientUiState.Error
    }

}