package com.example.truckercore.view_model.view_models.preparing_ambient

import androidx.lifecycle.ViewModel
import com.example.truckercore._utils.expressions.getName
import com.example.truckercore._utils.expressions.getOrElse
import com.example.truckercore._utils.expressions.launch
import com.example.truckercore.model.errors.AppException
import com.example.truckercore.model.logger.AppLogger
import com.example.truckercore.model.modules.aggregation.session.use_cases.GetSessionInfoUseCase
import com.example.truckercore.model.modules.authentication.manager.AuthManager
import kotlinx.coroutines.flow.asStateFlow

class PreparingAmbientViewModel(
    private val sessionInfoUseCase: GetSessionInfoUseCase,
    private val authManager: AuthManager
) : ViewModel() {

    private val stateManager = PreparingAmbientUiStateManager()
    val state get() = stateManager.state.asStateFlow()

    init {
        onEvent(PreparingAmbientEvent.LoadSession)
    }

    fun onEvent(newEvent: PreparingAmbientEvent) {
        when (newEvent) {
            PreparingAmbientEvent.LoadSession -> handleLoadSessionEvent()
            PreparingAmbientEvent.SessionLoaded -> handleSessionLoadedEvent()
            is PreparingAmbientEvent.ReceivedSessionError -> handleSessionError(newEvent.exception)
            is PreparingAmbientEvent.ReceivedUidError -> handleUidError(newEvent.exception)
        }
    }

    private fun handleLoadSessionEvent() {
        AppLogger.d(getName(), LOAD_SESSION_MSG)
        loadSession()
    }

    private fun handleSessionLoadedEvent() {
        AppLogger.d(getName(), SESSION_LOADED_MSG)
        stateManager.setSuccessState()
    }

    private fun handleSessionError(exception: AppException) {
        AppLogger.d(getName(), "$RECEIVED_SESSION_ERROR_MSG $exception")
        stateManager.setSessionErrorState()
    }

    private fun handleUidError(exception: AppException) {
        AppLogger.d(getName(), "$RECEIVED_UID_ERROR_MSG $exception")
        stateManager.setUidErrorState()
    }

    private fun loadSession() {
        launch {
            val uid = authManager.getUID().getOrElse {
                onEvent(PreparingAmbientEvent.ReceivedUidError(it.exception))
                return@launch
            }
            sessionInfoUseCase(uid).getOrElse {
                onEvent(PreparingAmbientEvent.ReceivedSessionError(it.exception))
                return@launch
            }
            onEvent(PreparingAmbientEvent.SessionLoaded)
        }
    }

    companion object {
        private const val LOAD_SESSION_MSG =
            "Loading session: triggering session fetch."

        private const val RECEIVED_SESSION_ERROR_MSG =
            "API error received while loading session. Error:"

        private const val RECEIVED_UID_ERROR_MSG =
            "API error received while loading UID. Error:"

        private const val SESSION_LOADED_MSG =
            "Session successfully loaded and stored."
    }

}

