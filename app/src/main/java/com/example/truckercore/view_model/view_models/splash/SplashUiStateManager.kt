package com.example.truckercore.view_model.view_models.splash

import com.example.truckercore._utils.expressions.getClassName
import com.example.truckercore.model.logger.AppLogger
import com.example.truckercore.view.ui_error.UiError
import kotlinx.coroutines.flow.MutableStateFlow

class SplashUiStateManager {

    private var _uiState: MutableStateFlow<SplashUiState> = MutableStateFlow(SplashUiState.Initial)
    val uiState get() = _uiState

    private lateinit var direction: SplashUiState.Navigating

    fun setLoadingState() {
        _uiState.value = SplashUiState.Loading
    }

    fun setNavigatingState() {
        val newState = when {
            ::direction.isInitialized -> direction
            else -> {
                AppLogger.e(getClassName(), DIRECTION_UNINITIALIZED)
                SplashUiState.Error(UiError.Critical())
            }
        }

        _uiState.value = newState
    }

    fun holdDirection(stateDirection: SplashUiState.Navigating) {
        direction = stateDirection
    }

    fun setErrorState() {
        _uiState.value = SplashUiState.Error(UiError.Critical())
    }

    companion object {
        private const val DIRECTION_UNINITIALIZED = "Variable direction was not initialized."
    }
}
