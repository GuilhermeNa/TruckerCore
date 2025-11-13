package com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth

import com.example.truckercore.core.my_lib.expressions.launchOnViewModelScope
import com.example.truckercore.layers.domain.use_case.authentication.CreateUserWithEmailUseCase
import com.example.truckercore.layers.presentation.viewmodels.base._base.managers.EffectManager
import com.example.truckercore.layers.presentation.viewmodels.base._base.managers.StateManager
import com.example.truckercore.layers.presentation.viewmodels.base.abstractions.BaseViewModel
import com.example.truckercore.layers.presentation.viewmodels.base.expressions.handle
import com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.effect.EmailAuthenticationFragmentEffect
import com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.event.EmailAuthenticationFragmentEvent
import com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.reducer.EmailAuthenticationFragmentReducer
import com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.state.EmailAuthenticationFragmentState
import com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.use_case.AuthenticationViewUseCase
import kotlinx.coroutines.delay

class EmailAuthViewModel(
    private val createUserWithEmailUseCase: CreateUserWithEmailUseCase,
    private val authViewUseCase: AuthenticationViewUseCase
) : BaseViewModel() {

    private val initialUiState by lazy { EmailAuthenticationFragmentState() }

    private val stateManager = StateManager(initialUiState)
    val stateFlow get() = stateManager.stateFlow

    private val effectManager = EffectManager<EmailAuthenticationFragmentEffect>()
    val effectFlow get() = effectManager.effectFlow

    private val reducer = EmailAuthenticationFragmentReducer()

    //----------------------------------------------------------------------------------------------
    fun onEvent(event: EmailAuthenticationFragmentEvent) = launchOnViewModelScope {
        val result = reducer.reduce(stateManager.currentState(), event)
        result.handle(stateManager::update, ::handleEffect)
    }

    fun handleEffect(effect: EmailAuthenticationFragmentEffect) {
        when (effect) {
            EmailAuthenticationFragmentEffect.Task.AuthenticateEmail -> authenticateEmail()
            else -> effectManager::trySend
        }
    }

    private fun authenticateEmail() = launchOnViewModelScope {
        delay(500)
        val credential = stateManager.currentState().getCredential()

        //TODO(Emitir novo evento de acordo com o resultado da task)
        val newEvent = authViewUseCase(credential).map()
        onEvent(newEvent)
    }

}