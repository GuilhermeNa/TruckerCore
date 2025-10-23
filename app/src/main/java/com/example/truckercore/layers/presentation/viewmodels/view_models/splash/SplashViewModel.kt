package com.example.truckercore.layers.presentation.viewmodels.view_models.splash

import androidx.lifecycle.viewModelScope
import com.example.truckercore.core.expressions.getClassName
import com.example.truckercore.core.expressions.logEvent
import com.example.truckercore.core.util.AppLogger
import com.example.truckercore.domain._shared.expressions.handleResult
import com.example.truckercore.domain._shared.expressions.mapResult
import com.example.truckercore.domain.view_models.splash.effect.SplashEffect
import com.example.truckercore.domain.view_models.splash.use_case.SplashDirection
import com.example.truckercore.domain.view_models.splash.use_case.SplashViewUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    private val splashViewUseCase: SplashViewUseCase,
    private val flavorService: com.example.truckercore.core.config.flavor.FlavorService
) : com.example.truckercore.presentation.viewmodels._shared._base.view_model.LoggerViewModel() {

    private val stateManager =
        com.example.truckercore.presentation.viewmodels._shared._base.managers.StateManagerII(com.example.truckercore.presentation.viewmodels.view_models.splash.state.SplashState())
    val stateFlow get() = stateManager.stateFlow

    private val effectManager =
        com.example.truckercore.presentation.viewmodels._shared._base.managers.EffectManagerII<SplashEffect>()
    val effectFlow get() = effectManager.effectFlow

    private lateinit var direction: SplashDirection

    private val reducer =
        com.example.truckercore.presentation.viewmodels.view_models.splash.SplashReducer()

    //----------------------------------------------------------------------------------------------
    init {
        val appName = flavorService.getAppName()
        val event = com.example.truckercore.layers.presentation.viewmodels.view_models.splash.event.SplashEvent.SystemEvent.Initialize(appName)
        onEvent(event)
    }

    fun toLoadingTransitionEnd() {
        val event = com.example.truckercore.layers.presentation.viewmodels.view_models.splash.event.SplashEvent.TransitionEnd.ToLoading
        onEvent(event)
    }

    fun toLoadedTransitionEnd() {
        val event = com.example.truckercore.layers.presentation.viewmodels.view_models.splash.event.SplashEvent.TransitionEnd.ToLoaded(direction)
        onEvent(event)
    }

    private fun onEvent(newEvent: com.example.truckercore.layers.presentation.viewmodels.view_models.splash.event.SplashEvent) {
        logEvent(this, newEvent)
        reducer.reduce(stateManager.currentState(), newEvent).handleResult(
            state = { stateManager.update(it) },
            effect = {
                when (it) {
                    is SplashEffect.SystemEffect.ExecuteLoadUserTask -> loadUserInfo()
                    else -> effectManager.trySend(it)
                }
            }
        )
    }

    private fun loadUserInfo() {
        AppLogger.d(getClassName(), TASK_LAUNCH)
        viewModelScope.launch {
            delay(TIMER)
            val newEvent = splashViewUseCase.invoke().mapResult(
                onSuccess = { direction ->
                    AppLogger.d(this@SplashViewModel.getClassName(), TASK_SUCCESS)
                    this@SplashViewModel.direction = direction
                    com.example.truckercore.layers.presentation.viewmodels.view_models.splash.event.SplashEvent.SystemEvent.LoadUserTask.Success
                },
                onCriticalError = {
                    AppLogger.d(this@SplashViewModel.getClassName(), TASK_CRITICAL_ERROR)
                    com.example.truckercore.layers.presentation.viewmodels.view_models.splash.event.SplashEvent.SystemEvent.LoadUserTask.CriticalError
                                  },
                onRecoverableError = {
                    AppLogger.d(this@SplashViewModel.getClassName(), TASK_RECOVERABLE_ERROR)
                    com.example.truckercore.layers.presentation.viewmodels.view_models.splash.event.SplashEvent.SystemEvent.LoadUserTask.CriticalError
                }
            )
            onEvent(newEvent)
        }
    }

    private companion object {
        private const val TASK_LAUNCH = "Task: LoadUserInfo -> Launched"
        private const val TASK_SUCCESS = "Task: LoadUserInfo -> Success"
        private const val TASK_CRITICAL_ERROR = "Task: LoadUserInfo -> Critical Error"
        private const val TASK_RECOVERABLE_ERROR = "Task: LoadUserInfo -> Recoverable Error"
        private const val TIMER = 1000L
    }

}