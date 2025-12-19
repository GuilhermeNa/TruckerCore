package com.example.truckercore.layers.presentation.login.view_model.login

import androidx.lifecycle.viewModelScope
import com.example.truckercore.core.my_lib.classes.EmailCredential
import com.example.truckercore.core.my_lib.expressions.handle
import com.example.truckercore.layers.data.base.outcome.OperationOutcome
import com.example.truckercore.core.my_lib.expressions.isConnectionError
import com.example.truckercore.core.my_lib.expressions.isInvalidCredential
import com.example.truckercore.core.my_lib.expressions.isInvalidUser
import com.example.truckercore.core.my_lib.expressions.isUserNotFound
import com.example.truckercore.layers.data.repository.preferences.PreferencesRepository
import com.example.truckercore.layers.domain.use_case.authentication.SignInUseCase
import com.example.truckercore.layers.presentation.base.abstractions.view_model.BaseViewModel
import com.example.truckercore.layers.presentation.base.managers.EffectManager
import com.example.truckercore.layers.presentation.base.managers.StateManager
import com.example.truckercore.layers.presentation.login.view.fragments.login.LoginFragment
import com.example.truckercore.layers.presentation.login.view_model.login.helpers.LoginFragmentEffect
import com.example.truckercore.layers.presentation.login.view_model.login.helpers.LoginFragmentEvent
import com.example.truckercore.layers.presentation.login.view_model.login.helpers.LoginFragmentReducer
import com.example.truckercore.layers.presentation.login.view_model.login.helpers.LoginFragmentState
import kotlinx.coroutines.launch

/**
 * This ViewModel coordinates the login flow for [LoginFragment]. It:
 * - Processes UI events via a reducer (unidirectional data flow)
 * - Updates immutable UI state via a [StateManager]
 * - Emits one-time effects for navigation or messages via an [EffectManager]
 * - Executes login use cases and persists preferences when requested
 *
 * It follows a predictable MVI-style structure:
 *   UI Event → Reducer → New State / Effect → UI Rendering
 */
class LoginViewModel(
    private val loginUseCase: SignInUseCase,
    private val preferences: PreferencesRepository
) : BaseViewModel() {

    //----------------------------------------------------------------------------------------------
    // State & Effect Streams
    //----------------------------------------------------------------------------------------------
    private val stateManager = StateManager(LoginFragmentState())
    val stateFlow get() = stateManager.stateFlow

    private val effectManager = EffectManager<LoginFragmentEffect>()
    val effectFlow get() = effectManager.effectFlow

    // Reducer responsible for translating events into state updates or effects
    private val reducer = LoginFragmentReducer()

    //----------------------------------------------------------------------------------------------
    // Event Handling
    //----------------------------------------------------------------------------------------------
    /**
     * Entry point for all UI events. Each event is processed by the reducer,
     * which returns a result indicating new state or effects.
     */
    fun onEvent(event: LoginFragmentEvent) {
        val result = reducer.reduce(stateManager.currentState(), event)

        // The reducer result decides whether to update state or trigger effects
        result.handle(stateManager::update, ::handleEffect)
    }

    /**
     * Handles effects produced by the reducer. Effects represent one-time actions
     * such as starting the login task, updating preferences, or triggering navigation.
     */
    private fun handleEffect(effect: LoginFragmentEffect) {
        when (effect) {
            is LoginFragmentEffect.LaunchLoginTask ->
                tryToLogin(effect.credential)

            is LoginFragmentEffect.UpdatePreferences ->
                updateKeepLoggedPreferences(effect.keepLogged)

            else -> effectManager.trySend(effect) // Forward to UI layer
        }
    }

    //----------------------------------------------------------------------------------------------
    // Login Operations
    //----------------------------------------------------------------------------------------------
    /**
     * Executes the login use case asynchronously. When the operation completes,
     * its outcome is converted into a new UI event to continue the state flow.
     */
    private fun tryToLogin(credential: EmailCredential) {
        viewModelScope.launch {
            val outcome = loginUseCase(credential)
            val newEvent = outcome.toLoginEvent()
            onEvent(newEvent) // Feed result back into reducer
        }
    }

    /**
     * Updates a user preference indicating whether the user wants to stay logged in.
     */
    private fun updateKeepLoggedPreferences(keepLogged: Boolean) {
        viewModelScope.launch {
            preferences.setKeepLogged(keepLogged)
        }
    }

    //----------------------------------------------------------------------------------------------
    // Outcome Mapping
    //----------------------------------------------------------------------------------------------
    /**
     * Converts a domain-level OperationOutcome into a specific ViewModel event
     * that the reducer understands. Keeps the domain layer decoupled from the UI layer.
     */
    private fun OperationOutcome.toLoginEvent() = when (this) {
        OperationOutcome.Completed ->
            LoginFragmentEvent.LoginTask.Complete

        is OperationOutcome.Failure -> when {
            isConnectionError() ->
                LoginFragmentEvent.LoginTask.NoConnection

            isInvalidCredential() || isInvalidUser() || isUserNotFound() ->
                LoginFragmentEvent.LoginTask.InvalidCredential

            else ->
                LoginFragmentEvent.LoginTask.Failure
        }
    }

    //----------------------------------------------------------------------------------------------
    // Public API
    //----------------------------------------------------------------------------------------------
    /**
     * Called directly from the fragment when the user presses the login button.
     * Wraps the intent into a Retry event for the reducer to handle.
     */
    fun tryLogin(credential: EmailCredential) {
        onEvent(LoginFragmentEvent.Retry(credential))
    }

}

