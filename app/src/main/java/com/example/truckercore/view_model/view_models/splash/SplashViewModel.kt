package com.example.truckercore.view_model.view_models.splash

import androidx.lifecycle.viewModelScope
import com.example.truckercore._shared.expressions.getClassName
import com.example.truckercore._shared.expressions.getOrElse
import com.example.truckercore._shared.expressions.launch
import com.example.truckercore._shared.expressions.logEvent
import com.example.truckercore.model.infrastructure.integration.preferences.PreferencesRepository
import com.example.truckercore.model.logger.AppLogger
import com.example.truckercore.model.modules.aggregation.system_access.manager.SystemAccessManager
import com.example.truckercore.model.modules.authentication.manager.AuthManager
import com.example.truckercore.view_model._shared._base.view_model.LoggerViewModel
import com.example.truckercore.view_model.view_models.splash.effect.SplashEffectManager
import com.example.truckercore.view_model.view_models.splash.event.SplashEvent
import com.example.truckercore.view_model.view_models.splash.state.SplashState
import com.example.truckercore.view_model.view_models.splash.state.SplashUiStateManager
import com.example.truckercore.view_model.view_models.splash.use_case.SplashViewUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val splashViewUseCase: SplashViewUseCase,
    private val authService: AuthManager,
    private val preferences: PreferencesRepository,
    private val systemAccessManager: SystemAccessManager
) : LoggerViewModel() {

    private val stateManager = SplashUiStateManager()
    val uiState get() = stateManager.uiState.asStateFlow()

    private val effectManager = SplashEffectManager()
    val effect get() = effectManager.effect.asSharedFlow()

    init {
        onEvent(SplashEvent.UiEvent.Initialized)
    }

    //----------------------------------------------------------------------------------------------
    fun initialize() {

    }

    fun onEvent(event: SplashEvent) {
        logEvent(this@SplashViewModel, event)
        when (event) {
            is SplashEvent.UiEvent -> handleUiEvent(event)
            is SplashEvent.SystemEvent -> handleSystemEvent(event)
        }
    }

    private fun handleUiEvent(event: SplashEvent.UiEvent) {
        when (event) {
            SplashEvent.UiEvent.Initialized -> {
                launch { effectManager.setTransitionToLoading() }
            }
            SplashEvent.UiEvent.TransitionToLoadingComplete -> {
                stateManager.setLoadingState()
                loadUserInfo()
            }
            SplashEvent.UiEvent.TransitionToNavigationComplete -> {
                stateManager.setNavigatingState()
            }
        }
    }

    private fun handleSystemEvent(event: SplashEvent.SystemEvent) {
        when (event) {
            is SplashEvent.SystemEvent.ReceivedApiSuccess -> {
                stateManager.holdDirection(event.direction)
                launch { effectManager.setTransitionToNavigation() }
            }
            is SplashEvent.SystemEvent.Error -> {
                stateManager.setErrorState()
            }
        }
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            delay(1000)
            try {
                val direction = when  {
                    isFirstAccess() -> SplashState.Navigating.Welcome
                    sessionNotFound() -> SplashState.Navigating.Login
                    isUserRegisterComplete() -> SplashState.Navigating.PreparingAmbient
                    else -> SplashState.Navigating.ContinueRegister
                }

                val newEvent = SplashEvent.SystemEvent.ReceivedApiSuccess(direction)
                AppLogger.d(this@SplashViewModel.getClassName(), "$LOAD_INFO_SUCCESS $direction")
                onEvent(newEvent)

            } catch (e: Exception) {
                AppLogger.e(this@SplashViewModel.getClassName(), LOAD_INFO_ERROR, e)
                onEvent(SplashEvent.SystemEvent.Error)
            }
        }
    }

    private suspend fun isFirstAccess(): Boolean {
        val isFirstAccess = preferences.isFirstAccess()
        val hasLoggedUser = authService.thereIsLoggedUser()

        if (isFirstAccess && hasLoggedUser) {
            authService.signOut()
        }

        return isFirstAccess
    }

    private fun sessionNotFound(): Boolean {
        return !authService.thereIsLoggedUser()
    }

    private suspend fun isUserRegisterComplete(): Boolean {
        // Check if Email is Verified
        val isEmailVerified = authService.isEmailVerified().getOrElse { throw it.exception }
        if (!isEmailVerified) return false

        // Check if user have been registered in system
        val uid = authService.getUID().getOrElse { throw it.exception }
        return systemAccessManager.isUserRegistered(uid).getOrElse { throw it.exception }
    }

    companion object {
        private const val LOAD_INFO_ERROR = "Failed on loading user info."
        private const val LOAD_INFO_SUCCESS = "Success on loading user info. Direction:"
    }

}