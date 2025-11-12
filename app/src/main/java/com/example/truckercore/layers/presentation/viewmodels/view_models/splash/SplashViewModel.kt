package com.example.truckercore.layers.presentation.viewmodels.view_models.splash

import androidx.lifecycle.viewModelScope
import com.example.truckercore.core.config.flavor.FlavorService
import com.example.truckercore.core.my_lib.files.ONE_SEC
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.base.outcome.expressions.getRequired
import com.example.truckercore.layers.data.base.outcome.expressions.isNotSuccess
import com.example.truckercore.layers.data.repository.preferences.PreferencesRepository
import com.example.truckercore.layers.domain.base.enums.RegistrationStatus
import com.example.truckercore.layers.domain.use_case.authentication.CheckUserRegistrationUseCase
import com.example.truckercore.layers.presentation.viewmodels.base._base.managers.EffectManager
import com.example.truckercore.layers.presentation.viewmodels.base._base.managers.StateManager
import com.example.truckercore.layers.presentation.viewmodels.base.abstractions.BaseViewModel
import com.example.truckercore.layers.presentation.viewmodels.base.expressions.handle
import com.example.truckercore.layers.presentation.viewmodels.view_models.splash.effect.SplashEffect
import com.example.truckercore.layers.presentation.viewmodels.view_models.splash.event.SplashEvent
import com.example.truckercore.layers.presentation.viewmodels.view_models.splash.state.SplashState
import com.example.truckercore.layers.presentation.viewmodels.view_models.splash.effect.SplashDirection
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private typealias TaskSuccess = SplashEvent.SystemEvent.LoadUserTask.Success
private typealias TaskError = SplashEvent.SystemEvent.LoadUserTask.Error

class SplashViewModel(
    private val checkUserRegistration: CheckUserRegistrationUseCase,
    private val preferences: PreferencesRepository,
    flavorService: FlavorService
) : BaseViewModel() {

    private val stateManager = StateManager(SplashState())
    val stateFlow get() = stateManager.stateFlow

    private val effectManager = EffectManager<SplashEffect>()
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
        reducer.reduce(stateManager.currentState(), newEvent).handle(
            state = stateManager::update,
            effect = ::handleEffect
        )
    }

    private fun handleEffect(effect: SplashEffect) {
        when (effect) {
            is SplashEffect.SystemEffect.ExecuteLoadUserTask -> loadUserInfo()
            else -> effectManager.trySend(effect)
        }
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            delay(ONE_SEC)

            val outcome = checkUserRegistration()
            val newEvent = when {
                outcome.isNotSuccess() -> TaskError
                else -> handleSuccessTask(outcome)
            }

            onEvent(newEvent)
        }
    }

    private suspend fun handleSuccessTask(outcome: DataOutcome<RegistrationStatus>): TaskSuccess {
        direction = when (outcome.getRequired()) {
            RegistrationStatus.ACCOUNT_NOT_FOUND -> when {
                preferences.isFirstAccess() -> SplashDirection.WELCOME
                else -> SplashDirection.LOGIN
            }

            RegistrationStatus.COMPLETE -> SplashDirection.MAIN

            else -> SplashDirection.CONTINUE_REGISTER
        }
        return TaskSuccess
    }

}