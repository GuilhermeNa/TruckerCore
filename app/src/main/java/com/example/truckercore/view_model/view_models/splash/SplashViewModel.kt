package com.example.truckercore.view_model.view_models.splash

import androidx.lifecycle.viewModelScope
import com.example.truckercore._shared.expressions.logEvent
import com.example.truckercore.model.configs.flavor.FlavorService
import com.example.truckercore.view_model._shared._base.view_model.LoggerViewModel
import com.example.truckercore.view_model._shared.expressions.mapResult
import com.example.truckercore.view_model.view_models.splash.effect.SplashEffectManager
import com.example.truckercore.view_model.view_models.splash.event.SplashEvent
import com.example.truckercore.view_model.view_models.splash.state.SplashStateManager
import com.example.truckercore.view_model.view_models.splash.use_case.SplashDirection
import com.example.truckercore.view_model.view_models.splash.use_case.SplashViewUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    private val splashViewUseCase: SplashViewUseCase,
    private val flavorService: FlavorService
) : LoggerViewModel() {

    private val stateManager = SplashStateManager()
    val stateFlow get() = stateManager.stateFlow

    private val effectManager = SplashEffectManager()
    val effectFlow get() = effectManager.effectFlow

    //----------------------------------------------------------------------------------------------
    fun initialize() {
        onEvent(SplashEvent.SystemEvent.Initialize)
    }

    fun notifyLoadingTransitionEnd() {
        onEvent(SplashEvent.UiTransition.ToLoading.End)
    }

    fun notifyLoadedTransitionEnd() {
        onEvent(SplashEvent.UiTransition.ToLoaded.End)
    }

    private fun onEvent(newEvent: SplashEvent) {
        logEvent(this, newEvent)
        when (newEvent) {
            is SplashEvent.UiTransition.ToLoading -> handleUiTransitionToLoading(newEvent)
            is SplashEvent.UiTransition.ToLoaded -> handleUiTransitionToLoaded(newEvent)
            is SplashEvent.SystemEvent.Initialize -> handleInitializationEvent()
            is SplashEvent.SystemEvent.LoadUserTask -> handleLoadUserTask(newEvent)
        }
    }

    private fun handleUiTransitionToLoading(transitionEvent: SplashEvent.UiTransition.ToLoading) {
        when (transitionEvent) {
            SplashEvent.UiTransition.ToLoading.Start -> effectManager.setTransitionToLoading()
            SplashEvent.UiTransition.ToLoading.End -> onEvent(SplashEvent.SystemEvent.LoadUserTask.Execute)
        }
    }

    private fun handleUiTransitionToLoaded(transitionEvent: SplashEvent.UiTransition.ToLoaded) {
        when (transitionEvent) {
            SplashEvent.UiTransition.ToLoaded.Start -> effectManager.setTransitionToLoaded()
            SplashEvent.UiTransition.ToLoaded.End -> {
                val direction = stateManager.getDirection()
                when (direction) {
                    SplashDirection.WELCOME -> effectManager.setNavigateToWelcomeEffect()
                    SplashDirection.LOGIN -> effectManager.setNavigateToLoginEffect()
                    SplashDirection.MAIN -> effectManager.setNavigateToMainEffect()
                    SplashDirection.CONTINUE_REGISTER -> effectManager.setNavigateToContinueEffect()
                }
            }
        }
    }

    private fun handleInitializationEvent() {
        val appName = flavorService.getAppName()
        stateManager.updateAppNameComponent(appName)
        onEvent(SplashEvent.UiTransition.ToLoading.Start)
    }

    private fun handleLoadUserTask(taskEvent: SplashEvent.SystemEvent.LoadUserTask) {
        when (taskEvent) {
            SplashEvent.SystemEvent.LoadUserTask.Execute -> {
                stateManager.setLoadingState()
                loadUserInfo()
            }

            is SplashEvent.SystemEvent.LoadUserTask.Success -> {
                stateManager.setLoadedState(taskEvent.direction)
                onEvent(SplashEvent.UiTransition.ToLoaded.Start)
            }

            SplashEvent.SystemEvent.LoadUserTask.CriticalError -> {
                effectManager.setNavigateToNotificationEffect()
            }
        }
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            delay(TIMER)
            val newEvent = splashViewUseCase.invoke().mapResult(
                onSuccess = { SplashEvent.SystemEvent.LoadUserTask.Success(it) },
                onCriticalError = { SplashEvent.SystemEvent.LoadUserTask.CriticalError },
                onRecoverableError = { SplashEvent.SystemEvent.LoadUserTask.CriticalError }
            )
            onEvent(newEvent)
        }
    }

    private companion object {
        private const val TIMER = 1000L
    }

}