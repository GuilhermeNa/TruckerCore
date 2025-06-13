package com.example.truckercore.view_model.view_models.splash.state

import com.example.truckercore._shared.expressions.getClassName
import com.example.truckercore.model.logger.AppLogger
import com.example.truckercore.view_model._shared.helpers.ViewError
import kotlinx.coroutines.flow.MutableStateFlow

class SplashUiStateManager {

    private var _uiState: MutableStateFlow<SplashState> = MutableStateFlow(SplashState.Initial)
    val uiState get() = _uiState

    private lateinit var direction: SplashState.Navigating

    fun setLoadingState() {
        _uiState.value = SplashState.Loading
    }

    fun setNavigatingState() {
        val newState = when {
            ::direction.isInitialized -> direction
            else -> {
                AppLogger.e(getClassName(), DIRECTION_UNINITIALIZED)
                SplashState.Error(ViewError.Critical)
            }
        }

        _uiState.value = newState
    }

    fun holdDirection(stateDirection: SplashState.Navigating) {
        direction = stateDirection
    }

    fun setErrorState() {
        _uiState.value = SplashState.Error(ViewError.Critical)
    }

    companion object {
        private const val DIRECTION_UNINITIALIZED = "Variable direction was not initialized."
    }
}
