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
import com.example.truckercore.layers.presentation.login.view.fragments.splash.SplashFragment
import com.example.truckercore.layers.presentation.login.view_model.splash.helpers.SplashDirection
import com.example.truckercore.layers.presentation.login.view_model.splash.helpers.SplashEffect
import com.example.truckercore.layers.presentation.login.view_model.splash.helpers.SplashEvent
import com.example.truckercore.layers.presentation.login.view_model.splash.helpers.SplashReducer
import com.example.truckercore.layers.presentation.login.view_model.splash.helpers.SplashState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * This ViewModel coordinates the application splash flow for [SplashFragment]. It:
 *
 * - Orchestrates the initial application startup logic
 * - Applies persisted user preferences (e.g., "keep logged in")
 * - Loads and validates the current user session
 * - Determines the correct navigation path based on user registration status
 * - Exposes immutable UI state and one-time UI effects using an MVI-style approach
 *
 * Responsibilities include:
 * - Applying the "keep logged in" preference and signing out the user if necessary
 * - Initializing splash screen data (such as application name)
 * - Executing user loading and registration validation use cases
 * - Mapping domain outcomes into UI events
 * - Deciding the next application flow (login, welcome, registration, or check-in)
 * - Emitting navigation and transition effects in a controlled, testable manner
 *
 * The ViewModel follows a predictable MVI-style flow:
 *
 *   UI Event → Reducer → New State and/or Effect → UI Rendering
 *
 * Side effects (such as loading user data, applying preferences, and signing out)
 * are isolated and handled explicitly to ensure testability, traceability,
 * and a clear separation of concerns.
 */
class SplashViewModel(
    private val checkUserRegistrationUseCase: CheckUserRegistrationUseCase,
    private val preferences: PreferencesRepository,
    private val signOutUseCase: SignOutUseCase,
    flavorService: FlavorService
) : BaseViewModel() {

    //----------------------------------------------------------------------------------------------
    // State & Effect Streams
    //----------------------------------------------------------------------------------------------
    /**
     * Manages and exposes the immutable UI state for the splash screen.
     */
    private val stateManager = StateManager(SplashState())
    val stateFlow get() = stateManager.stateFlow

    /**
     * Emits one-time UI effects such as navigation commands and transition triggers.
     */
    private val effectManager = EffectManager<SplashEffect>()
    val effectFlow get() = effectManager.effectFlow

    /**
     * Holds the navigation direction resolved after user validation.
     * This value is consumed once the splash transition finishes.
     */
    private lateinit var direction: SplashDirection

    /**
     * Reducer responsible for translating UI and system events into
     * state updates and/or effects.
     */
    private val reducer = SplashReducer()

    //----------------------------------------------------------------------------------------------
    // Initialization
    //----------------------------------------------------------------------------------------------
    /**
     * Initializes the splash flow by:
     *
     * - Applying the "keep logged in" preference
     * - Fetching the application name from the current flavor
     * - Dispatching an initialization event to the reducer
     */
    init {
        // Sign out if needed
        applyKeepLoggedPreference()

        // Initialize app with correct flavor's name
        val appName = flavorService.appName
        val event = SplashEvent.SystemEvent.Initialize(appName)
        onEvent(event)
    }

    /**
     * Applies the persisted "keep logged in" preference.
     *
     * If the user opted not to stay logged in, the current session
     * is invalidated by executing the sign-out use case.
     */
    private fun applyKeepLoggedPreference() = viewModelScope.launch {
        if (!preferences.keepLogged()) signOutUseCase()
    }

    //----------------------------------------------------------------------------------------------
    // Transition Callbacks
    //----------------------------------------------------------------------------------------------
    /**
     * Notifies the ViewModel that the transition from the splash screen
     * to the loading state has finished.
     */
    fun toLoadingTransitionEnd() {
        val event = SplashEvent.TransitionEnd.ToLoading
        onEvent(event)
    }

    /**
     * Notifies the ViewModel that the transition from the loading state
     * to the final destination has finished.
     *
     * The resolved [SplashDirection] is passed to allow navigation.
     */
    fun toLoadedTransitionEnd() {
        val event = SplashEvent.TransitionEnd.ToLoaded(direction)
        onEvent(event)
    }

    //----------------------------------------------------------------------------------------------
    // Event Handling
    //----------------------------------------------------------------------------------------------
    /**
     * Entry point for all UI and system events.
     *
     * Events are processed by the reducer, which produces either
     * a new UI state or a one-time effect.
     */
    private fun onEvent(newEvent: SplashEvent) {
        reducer.reduce(stateManager.currentState(), newEvent).handle(
            state = stateManager::update,
            effect = ::handleEffect
        )
    }

    /**
     * Handles effects emitted by the reducer.
     *
     * Effects represent one-time operations such as executing background
     * tasks or triggering navigation events.
     */
    private fun handleEffect(effect: SplashEffect) {
        when (effect) {
            is SplashEffect.SystemEffect.ExecuteLoadUserTask -> loadUserInfo()
            else -> effectManager.trySend(effect)
        }
    }

    //----------------------------------------------------------------------------------------------
    // User Loading & Registration Validation
    //----------------------------------------------------------------------------------------------
    /**
     * Loads the current user information and validates the user's
     * registration status.
     *
     * A short delay is applied to ensure a smooth splash animation
     * before executing the registration check use case.
     *
     * The outcome is mapped into a UI event and fed back into the reducer,
     * preserving the unidirectional data flow.
     */
    private fun loadUserInfo() {
        viewModelScope.launch {
            delay(ONE_SEC)

            val outcome = checkUserRegistrationUseCase()
            val newEvent = outcome.toEvent()
            onEvent(newEvent)
        }
    }

    /**
     * Maps the domain outcome of the registration check into a splash event.
     */
    private suspend fun DataOutcome<RegistrationStatus>.toEvent() = when (this) {
        is DataOutcome.Failure, DataOutcome.Empty -> handleUnexpectedOutcome()

        is DataOutcome.Success -> {
            defineDirectionByRegistrationStatus(data)
            SplashEvent.SystemEvent.LoadUserTask.Success
        }
    }

    /**
     * Defines the navigation direction based on the user's registration status.
     *
     * - COMPLETE → Check-in flow
     * - INCOMPLETE → Registration completion flow
     * - ACCOUNT_NOT_FOUND → Welcome or Login flow
     */
    private suspend fun defineDirectionByRegistrationStatus(status: RegistrationStatus) {
        direction = when (status) {
            RegistrationStatus.ACCOUNT_NOT_FOUND ->
                handleAccountNotFound()

            RegistrationStatus.COMPLETE ->
                SplashDirection.CHECK_IN

            else ->
                SplashDirection.CONTINUE_REGISTER
        }
    }

    /**
     * Handles unexpected or invalid registration outcomes.
     *
     * Logs the error and returns a failure event to the reducer.
     */
    private fun handleUnexpectedOutcome(): SplashEvent.SystemEvent.LoadUserTask {
        AppLogger.e(getTag, REGISTRATION_ERROR_MSG)
        return SplashEvent.SystemEvent.LoadUserTask.Error
    }

    /**
     * Resolves the navigation direction when no account is found.
     *
     * - First access → Welcome flow
     * - Returning user → Login flow
     */
    private suspend fun handleAccountNotFound(): SplashDirection =
        if (preferences.isFirstAccess()) SplashDirection.WELCOME
        else SplashDirection.LOGIN

    //----------------------------------------------------------------------------------------------
    // Constants
    //----------------------------------------------------------------------------------------------
    private companion object {
        private const val REGISTRATION_ERROR_MSG =
            "Unexpected outcome while loading user registration status."
    }
}