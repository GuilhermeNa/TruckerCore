package com.example.truckercore.view_model.view_models.preparing_ambient

import androidx.lifecycle.ViewModel
import com.example.truckercore.model.modules.aggregation.session.use_cases.GetSessionInfoUseCase
import kotlinx.coroutines.flow.asStateFlow

class PreparingAmbientViewModel(
    private val sessionInfoUseCase: GetSessionInfoUseCase
): ViewModel() {

    private val stateManager = PreparingAmbientUiStateManager()
    val state get() = stateManager.state.asStateFlow()

    fun onEvent(newEvent: PreparingAmbientEvent) {
        when(newEvent) {
            PreparingAmbientEvent.LoadSessionRequest -> TODO()
            is PreparingAmbientEvent.ReceivedApiResult -> TODO()
            PreparingAmbientEvent.SessionLoaded -> TODO()
        }
    }

    private fun loadSession() {

    }

}