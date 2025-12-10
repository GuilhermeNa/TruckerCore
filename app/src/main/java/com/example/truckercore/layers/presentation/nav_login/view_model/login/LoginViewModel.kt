package com.example.truckercore.layers.presentation.nav_login.view_model.login

import androidx.lifecycle.viewModelScope
import com.example.truckercore.core.my_lib.classes.EmailCredential
import com.example.truckercore.core.my_lib.expressions.handle
import com.example.truckercore.layers.data.base.outcome.OperationOutcome
import com.example.truckercore.layers.data.base.outcome.expressions.isConnectionError
import com.example.truckercore.layers.data.base.outcome.expressions.isInvalidCredential
import com.example.truckercore.layers.data.base.outcome.expressions.isInvalidUser
import com.example.truckercore.layers.data.base.outcome.expressions.isUserNotFound
import com.example.truckercore.layers.data.repository.preferences.PreferencesRepository
import com.example.truckercore.layers.domain.use_case.authentication.SignInUseCase
import com.example.truckercore.layers.presentation.base.abstractions.view_model.BaseViewModel
import com.example.truckercore.layers.presentation.base.managers.EffectManager
import com.example.truckercore.layers.presentation.base.managers.StateManager
import com.example.truckercore.layers.presentation.nav_login.view_model.login.helpers.LoginFragmentEffect
import com.example.truckercore.layers.presentation.nav_login.view_model.login.helpers.LoginFragmentEvent
import com.example.truckercore.layers.presentation.nav_login.view_model.login.helpers.LoginFragmentReducer
import com.example.truckercore.layers.presentation.nav_login.view_model.login.helpers.LoginFragmentState
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: SignInUseCase,
    private val preferencesRepository: PreferencesRepository
) : BaseViewModel() {

    private val stateManager = StateManager(LoginFragmentState())
    val stateFlow get() = stateManager.stateFlow

    private val effectManager = EffectManager<LoginFragmentEffect>()
    val effectFlow get() = effectManager.effectFlow

    private val reducer = LoginFragmentReducer()

    //----------------------------------------------------------------------------------------------
    fun onEvent(event: LoginFragmentEvent) {
        val result = reducer.reduce(stateManager.currentState(), event)
        result.handle(stateManager::update, ::handleEffect)
    }

    private fun handleEffect(effect: LoginFragmentEffect) {
        when (effect) {
            is LoginFragmentEffect.LaunchLoginTask -> tryToLogin(effect.credential)
            else -> effectManager.trySend(effect)
        }
    }

    private fun tryToLogin(credential: EmailCredential) {
        viewModelScope.launch {
            val outcome = loginUseCase(credential)
            val event = outcome.toLoginEvent()
            onEvent(event)
        }
    }

    private fun OperationOutcome.toLoginEvent() = when (this) {
        OperationOutcome.Completed -> LoginFragmentEvent.LoginTask.Complete
        is OperationOutcome.Failure -> when {
            isConnectionError() -> LoginFragmentEvent.LoginTask.NoConnection

            isInvalidCredential() || isInvalidUser() || isUserNotFound() ->
                LoginFragmentEvent.LoginTask.InvalidCredential

            else -> LoginFragmentEvent.LoginTask.Failure
        }
    }

}

