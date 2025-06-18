package com.example.truckercore.view_model.view_models.splash

import androidx.lifecycle.viewModelScope
import com.example.truckercore._shared.expressions.getClassName
import com.example.truckercore._shared.expressions.logEvent
import com.example.truckercore.model.configs.flavor.FlavorService
import com.example.truckercore.model.logger.AppLogger
import com.example.truckercore.view_model._shared._base.managers.EffectManagerII
import com.example.truckercore.view_model._shared._base.managers.StateManagerII
import com.example.truckercore.view_model._shared._base.view_model.LoggerViewModel
import com.example.truckercore.view_model._shared.expressions.handleResult
import com.example.truckercore.view_model._shared.expressions.mapResult
import com.example.truckercore.view_model.view_models.splash.effect.SplashEffect
import com.example.truckercore.view_model.view_models.splash.event.SplashEvent
import com.example.truckercore.view_model.view_models.splash.state.SplashState
import com.example.truckercore.view_model.view_models.splash.use_case.SplashDirection
import com.example.truckercore.view_model.view_models.splash.use_case.SplashViewUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    private val splashViewUseCase: SplashViewUseCase,
    private val flavorService: FlavorService
) : LoggerViewModel() {

    private val stateManager = StateManagerII(SplashState())
    val stateFlow get() = stateManager.stateFlow

    private val effectManager = EffectManagerII<SplashEffect>()
    val effectFlow get() = effectManager.effectFlow

    private lateinit var direction: SplashDirection

    private val reducer = SplashReducer()

    //----------------------------------------------------------------------------------------------
    init {
        val appName = flavorService.getAppName()
        val event = SplashEvent.SystemEvent.Initialize(appName)
        onEvent(event)
    }

    fun toLoadingTransitionEnd() {
        val event = SplashEvent.TransitionEnd.ToLoading
        onEvent(event)
    }

    fun toLoadedTransitionEnd() {
        val event = SplashEvent.TransitionEnd.ToLoaded(direction)
        onEvent(event)
    }

    private fun onEvent(newEvent: SplashEvent) {
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
                    SplashEvent.SystemEvent.LoadUserTask.Success
                },
                onCriticalError = {
                    AppLogger.d(this@SplashViewModel.getClassName(), TASK_CRITICAL_ERROR)
                    SplashEvent.SystemEvent.LoadUserTask.CriticalError
                                  },
                onRecoverableError = {
                    AppLogger.d(this@SplashViewModel.getClassName(), TASK_RECOVERABLE_ERROR)
                    SplashEvent.SystemEvent.LoadUserTask.CriticalError
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