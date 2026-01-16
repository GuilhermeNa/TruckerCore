package com.example.truckercore.layers.presentation.login.view_model.splash

import androidx.lifecycle.viewModelScope
import com.example.truckercore.core.config.flavor.FlavorService
import com.example.truckercore.core.my_lib.expressions.getTag
import com.example.truckercore.core.my_lib.expressions.handle
import com.example.truckercore.core.my_lib.files.ONE_SEC
import com.example.truckercore.infra.logger.AppLogger
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.repository.preferences.PreferencesRepository
import com.example.truckercore.layers.domain.base.enums.RegistrationStatus
import com.example.truckercore.layers.domain.use_case.authentication.CheckUserRegistrationUseCase
import com.example.truckercore.layers.domain.use_case.authentication.SignOutUseCase
import com.example.truckercore.layers.presentation.base.abstractions.view_model.BaseViewModel
import com.example.truckercore.layers.presentation.base.managers.EffectManager
import com.example.truckercore.layers.presentation.base.managers.StateManager
import com.example.truckercore.layers.presentation.login.view_model.splash.helpers.SplashDirection
import com.example.truckercore.layers.presentation.login.view_model.splash.helpers.SplashEffect
import com.example.truckercore.layers.presentation.login.view_model.splash.helpers.SplashEvent
import com.example.truckercore.layers.presentation.login.view_model.splash.helpers.SplashReducer
import com.example.truckercore.layers.presentation.login.view_model.splash.helpers.SplashState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    private val checkUserRegistrationUseCase: CheckUserRegistrationUseCase,
    private val preferences: PreferencesRepository,
    flavorService: FlavorService,
    private val signOutUseCase: SignOutUseCase
) : BaseViewModel() {

    private val stateManager = StateManager(SplashState())
    val stateFlow get() = stateManager.stateFlow

    private val effectManager = EffectManager<SplashEffect>()
    val effectFlow get() = effectManager.effectFlow

    private lateinit var direction: SplashDirection

    private val reducer = SplashReducer()

    //----------------------------------------------------------------------------------------------
    init {
        // Check for keepLogged preferences and apply it
        applyKeepLoggedPreference()

        // Search for application context name
        val appName = flavorService.getAppName()
        val event = SplashEvent.SystemEvent.Initialize(appName)
        onEvent(event)
    }

    private fun applyKeepLoggedPreference() = viewModelScope.launch {
        if (!preferences.keepLogged()) signOutUseCase()
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

            val outcome = checkUserRegistrationUseCase()
            val newEvent = outcome.toEvent()
            onEvent(newEvent)
        }
    }

    private suspend fun DataOutcome<RegistrationStatus>.toEvent() = when (this) {
        is DataOutcome.Failure, DataOutcome.Empty -> handleUnexpectedOutcome()

        is DataOutcome.Success -> {
            defineDirectionByRegistrationStatus(data)
            SplashEvent.SystemEvent.LoadUserTask.Success
        }
    }

    private suspend fun defineDirectionByRegistrationStatus(status: RegistrationStatus) {
        direction = when (status) {
            RegistrationStatus.ACCOUNT_NOT_FOUND -> handleAccountNotFound()
            RegistrationStatus.COMPLETE -> SplashDirection.CHECK_IN
            else -> SplashDirection.CONTINUE_REGISTER
        }
    }

    private fun handleUnexpectedOutcome(): SplashEvent.SystemEvent.LoadUserTask {
        AppLogger.e(getTag, REGISTRATION_ERROR_MSG)
        return SplashEvent.SystemEvent.LoadUserTask.Error
    }

    private suspend fun handleAccountNotFound() =
        if (preferences.isFirstAccess()) SplashDirection.WELCOME else SplashDirection.LOGIN

    private companion object {
        private const val REGISTRATION_ERROR_MSG =
            "Unexpected outcome while loading user registration status."
    }

}