package com.example.truckercore.layers.presentation.nav_login.view_model.continue_register

import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.base.outcome.expressions.get
import com.example.truckercore.layers.data.base.outcome.expressions.isConnectionError
import com.example.truckercore.layers.data.base.outcome.expressions.isNotSuccess
import com.example.truckercore.layers.domain.base.enums.RegistrationStatus
import com.example.truckercore.layers.domain.use_case.authentication.CheckUserRegistrationUseCase
import com.example.truckercore.layers.domain.use_case.authentication.GetUserEmailUseCase
import com.example.truckercore.layers.presentation.nav_login.fragments.continue_register.ContinueRegisterFragment
import com.example.truckercore.layers.presentation.base.managers.EffectManager
import com.example.truckercore.layers.presentation.base.managers.StateManager
import com.example.truckercore.layers.presentation.base.reducer.Reducer
import com.example.truckercore.layers.presentation.base.abstractions.view_model.BaseViewModel
import com.example.truckercore.core.my_lib.expressions.handle
import com.example.truckercore.layers.presentation.viewmodels.view_models.continue_register.effect.ContinueRegisterFragmentEffect
import com.example.truckercore.layers.presentation.viewmodels.view_models.continue_register.event.ContinueRegisterFragmentEvent
import com.example.truckercore.layers.presentation.viewmodels.view_models.continue_register.reducer.ContinueRegisterFragmentReducer
import com.example.truckercore.layers.presentation.viewmodels.view_models.continue_register.state.ContinueRegisterFragmentState

/**
 * ViewModel responsible for managing the logic of the [ContinueRegisterFragment].
 *
 * @property checkUserRegistrationUseCase Use case that retrieves the current registration status.
 * @property getUserEmailUseCase Use case that retrieves the currently authenticated user email.
 */
class ContinueRegisterViewModel(
    private val checkUserRegistrationUseCase: CheckUserRegistrationUseCase,
    private val getUserEmailUseCase: GetUserEmailUseCase
) : BaseViewModel() {

    /**
     * Manages the current [ContinueRegisterFragmentState] and provides reactive updates to the UI.
     */
    private val stateManager = StateManager(ContinueRegisterFragmentState())
    val stateFlow get() = stateManager.stateFlow

    /**
     * Handles transient one-off [ContinueRegisterFragmentEffect] such as navigation actions.
     */
    private val effectManager = EffectManager<ContinueRegisterFragmentEffect>()
    val effectFlow get() = effectManager.effectFlow

    /**
     * The reducer that processes events and produces new states or effects.
     */
    private val reducer = ContinueRegisterFragmentReducer()

    /**
     * Stores the results of registration and email fetch operations.
     *
     * These outcomes are resolved during initialization and later interpreted
     * to produce the appropriate event that updates the screen.
     */
    private lateinit var registrationOutcome: DataOutcome<RegistrationStatus>
    private lateinit var emailOutcome: DataOutcome<Email>

    //----------------------------------------------------------------------------------------------
    // Initialization
    //----------------------------------------------------------------------------------------------
    /**
     * Initializes the screen by loading the registration status and user email.
     *
     * Once both outcomes are obtained, an appropriate [ContinueRegisterFragmentEvent]
     * is generated based on the success or failure of the operations.
     * This event is then dispatched to the [Reducer] through [onEvent].
     */
    fun initialize() {
        registrationOutcome = checkUserRegistrationUseCase()
        emailOutcome = getUserEmailUseCase()
        val newEvent = getEventAfterLoadData()
        onEvent(newEvent)
    }

    /**
     * Produces the event to be dispatched after registration data has been loaded.
     *
     * - If a connection error occurs, emits [Failure.NoConnection].
     * - If any other error or empty data is detected, emits [Failure.Irrecoverable].
     * - Otherwise, emits a [Complete] event with the resolved registration status and email.
     *
     * @return The event that reflects the data loading outcome.
     */
    private fun getEventAfterLoadData(): ContinueRegisterFragmentEvent = when {
        hasConnectionError() ->
            ContinueRegisterFragmentEvent.CheckRegisterTask.Failure.NoConnection

        hasAnyOtherErrorOrEmptyData() ->
            ContinueRegisterFragmentEvent.CheckRegisterTask.Failure.Irrecoverable

        else -> {
            val registration = registrationOutcome.get()
            val email = emailOutcome.get()
            ContinueRegisterFragmentEvent.CheckRegisterTask.Complete(registration, email)
        }
    }

    private fun hasConnectionError(): Boolean =
        registrationOutcome.isConnectionError() || emailOutcome.isConnectionError()

    private fun hasAnyOtherErrorOrEmptyData(): Boolean =
        registrationOutcome.isNotSuccess() || emailOutcome.isNotSuccess()

    //----------------------------------------------------------------------------------------------
    // Event Handling
    //----------------------------------------------------------------------------------------------
    /**
     * Called whenever a new [ContinueRegisterFragmentEvent] occurs.
     *
     * The event is delegated to the [reducer], and the resulting state or effect
     * is propagated via [stateManager] and [effectManager].
     *
     * @param newEvent The event emitted from the Continue Register Fragment.
     */
    fun onEvent(newEvent: ContinueRegisterFragmentEvent) {
        val result = reducer.reduce(stateManager.currentState(), newEvent)
        result.handle(stateManager::update, effectManager::trySend)
    }

}