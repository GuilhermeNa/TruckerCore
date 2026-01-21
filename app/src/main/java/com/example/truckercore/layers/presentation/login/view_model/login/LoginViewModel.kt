package com.example.truckercore.layers.presentation.login.view_model.login

import androidx.lifecycle.viewModelScope
import com.example.truckercore.core.my_lib.classes.EmailCredential
import com.example.truckercore.core.my_lib.expressions.handle
import com.example.truckercore.core.my_lib.expressions.isConnectionError
import com.example.truckercore.core.my_lib.expressions.isInvalidCredential
import com.example.truckercore.core.my_lib.expressions.isInvalidUser
import com.example.truckercore.core.my_lib.expressions.isUserNotFound
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.base.outcome.OperationOutcome
import com.example.truckercore.layers.data.repository.preferences.PreferencesRepository
import com.example.truckercore.layers.domain.base.enums.RegistrationStatus
import com.example.truckercore.layers.domain.use_case.authentication.CheckUserRegistrationUseCase
import com.example.truckercore.layers.domain.use_case.authentication.SignInUseCase
import com.example.truckercore.layers.presentation.base.abstractions.view_model.BaseViewModel
import com.example.truckercore.layers.presentation.base.managers.EffectManager
import com.example.truckercore.layers.presentation.base.managers.StateManager
import com.example.truckercore.layers.presentation.login.view.fragments.login.LoginFragment
import com.example.truckercore.layers.presentation.login.view_model.login.helpers.LoginFragmentEffect
import com.example.truckercore.layers.presentation.login.view_model.login.helpers.LoginFragmentEvent
import com.example.truckercore.layers.presentation.login.view_model.login.helpers.LoginFragmentReducer
import com.example.truckercore.layers.presentation.login.view_model.login.helpers.LoginFragmentState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * This ViewModel coordinates the login flow for [LoginFragment]. It:
 *
 * - Processes UI events via a reducer (unidirectional data flow / MVI)
 * - Maintains and updates immutable UI state through a [StateManager]
 * - Emits one-time UI effects (navigation, messages, etc.) via an [EffectManager]
 * - Executes authentication and registration-related use cases
 * - Persists user preferences when required
 *
 * Responsibilities include:
 * - Handling login attempts and retry actions
 * - Mapping domain outcomes into UI events
 * - Checking user registration status after authentication
 * - Updating "keep logged in" preferences asynchronously
 *
 * The ViewModel follows a predictable MVI-style flow:
 *
 *   UI Event → Reducer → New State and/or Effect → UI Rendering
 *
 * Side effects are isolated and handled explicitly to ensure testability,
 * traceability, and a clear separation of concerns.
 */
class LoginViewModel(
    private val loginUseCase: SignInUseCase,
    private val preferences: PreferencesRepository,
    private val checkRegistrationUseCase: CheckUserRegistrationUseCase
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

    suspend fun getKeepLoggedState() = preferences.keepLogged()

    //----------------------------------------------------------------------------------------------
    // Event Handling
    //----------------------------------------------------------------------------------------------
    /**
     * Entry point for all UI events. Each event is processed by the reducer,
     * which returns a result indicating new state or effects.
     */
    fun onEvent(event: LoginFragmentEvent) {
        val result = reducer.reduce(stateManager.currentState(), event)
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

            is LoginFragmentEffect.CheckRegistration ->
                checkRegistration()

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

    /**
     * Checks whether the current user already has a complete registration.
     *
     * This method executes the [CheckUserRegistrationUseCase] to determine the
     * user's registration status after authentication.
     *
     * - If the registration is complete, the user will proceed to the check-in flow.
     * - If the registration is incomplete, the user will be redirected to the
     *   registration completion flow.
     * - Any unexpected failure is handled as an error state.
     *
     * The domain outcome is mapped into a corresponding UI event and fed back
     * into the reducer, preserving the unidirectional data flow and keeping all
     * navigation decisions centralized.
     */
    private fun checkRegistration() {
        val outcome = checkRegistrationUseCase()
        val newEvent = outcome.toRegistrationEvent()
        onEvent(newEvent)
    }

    private fun DataOutcome<RegistrationStatus>.toRegistrationEvent() = when (this) {
        is DataOutcome.Success -> {
            if (data == RegistrationStatus.COMPLETE)
                LoginFragmentEvent.CheckRegistrationTask.Complete
            else LoginFragmentEvent.CheckRegistrationTask.Incomplete
        }

        else -> LoginFragmentEvent.CheckRegistrationTask.Failure
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

