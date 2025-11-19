package com.example.truckercore.layers.presentation.nav_login.view_model.user_name

import com.example.truckercore.core.my_lib.ui_components.TextInputComponent
import com.example.truckercore.layers.data.base.outcome.OperationOutcome
import com.example.truckercore.layers.data.base.outcome.expressions.get
import com.example.truckercore.layers.data.base.outcome.expressions.map
import com.example.truckercore.layers.domain.use_case.authentication.CreateUserProfileUseCase
import com.example.truckercore.layers.domain.use_case.authentication.HasLoggedUserUseCase
import com.example.truckercore.layers.presentation.base.managers.EffectManager
import com.example.truckercore.layers.presentation.base.managers.StateManager
import com.example.truckercore.layers.presentation.base.abstractions.view_model.BaseViewModel
import com.example.truckercore.core.my_lib.expressions.handle
import com.example.truckercore.layers.presentation.viewmodels.view_models.user_name.effect.UserProfileFragmentEffect
import com.example.truckercore.layers.presentation.viewmodels.view_models.user_name.event.UserProfileFragmentEvent
import com.example.truckercore.layers.presentation.viewmodels.view_models.user_name.reducer.UserProfileFragmentReducer
import com.example.truckercore.layers.presentation.viewmodels.view_models.user_name.state.UserProfileFragmentState
import com.example.truckercore.layers.presentation.viewmodels.view_models.user_name.state.UserProfileFragmentStatus

/**
 * ViewModel responsible for managing all logic related to the user profile creation screen.
 *
 * It coordinates:
 * - Input validation and UI state transitions.
 * - Execution of use cases for verifying login status and creating the user profile.
 * - Emission of one-time [UserProfileFragmentEffect] navigation or processing events.
 *
 * The ViewModel uses:
 * - A [StateManager] to store and update [UserProfileFragmentState].
 * - An [EffectManager] to emit non-persistent UI effects.
 * - A [UserProfileFragmentReducer] to process incoming events.
 */
class UserProfileViewModel(
    private val hasLoggedUserUseCase: HasLoggedUserUseCase,
    private val createUserProfileUseCase: CreateUserProfileUseCase
) : BaseViewModel() {

    /**
     * Lazily constructed initial state for the fragment.
     * Contains:
     * - A basic [TextInputComponent] for the user name field.
     * - Initial status indicating that no input has been provided yet.
     */
    private val initialState by lazy {
        UserProfileFragmentState(
            nameComponent = TextInputComponent(),
            status = UserProfileFragmentStatus.WaitingInput
        )
    }

    // State & Effect Management
    private val stateManager = StateManager(initialState)
    val stateFlow get() = stateManager.stateFlow

    private val effectManager = EffectManager<UserProfileFragmentEffect>()
    val effectFlow get() = effectManager.effectFlow

    private val reducer = UserProfileFragmentReducer()

    //----------------------------------------------------------------------------------------------
    //
    //----------------------------------------------------------------------------------------------
    /**
     * Called by the fragment when it is first created.
     * Verifies whether the user is already authenticated. If not, triggers a navigation event
     * via [UserProfileFragmentEvent.UserNotLogged].
     */
    fun initialize() {
        if (!hasLoggedUserUseCase().get()) {
            onEvent(UserProfileFragmentEvent.UserNotLogged)
        }
    }

    /**
     * Primary entry point for all UI-triggered events.
     * Delegates event processing to the [UserProfileFragmentReducer], then:
     * - Updates the state via [StateManager.update] when necessary.
     * - Emits effects via [handleEffect].
     *
     * @param newEvent The event originating from the UI or internal operations.
     */
    fun onEvent(newEvent: UserProfileFragmentEvent) {
        val result = reducer.reduce(stateManager.currentState(), newEvent)
        result.handle(stateManager::update, ::handleEffect)
    }

    /**
     * Handles effects produced by the reducer.
     * - Launches the profile creation task when requested.
     * - Delegates navigation-related effects to the [EffectManager].
     *
     * @param effect A one-time UI instruction to be executed by the fragment.
     */
    private fun handleEffect(effect: UserProfileFragmentEffect) {
        when (effect) {
            UserProfileFragmentEffect.ProfileTask ->
                launchSaveProfileTask()

            is UserProfileFragmentEffect.Navigation ->
                effectManager::trySend
        }
    }

    /**
     * Launches the user profile creation process.
     * Extracts the current name from the state, executes the use case,
     * maps the result to an event, and feeds it back into the state machine.
     */
    private fun launchSaveProfileTask() {
        val name = stateManager.currentState().getName()
        val result = createUserProfileUseCase(name).toEvent()
        onEvent(result)
    }

    /**
     * Converts an [OperationOutcome] returned by the use case into the
     * appropriate [UserProfileFragmentEvent].
     *
     * - Success → [UserProfileFragmentEvent.ProfileTask.Complete]
     * - Failure by network → [UserProfileFragmentEvent.ProfileTask.Failure.NoConnection]
     * - Any other failure → [UserProfileFragmentEvent.ProfileTask.Failure.Irrecoverable]
     */
    private fun OperationOutcome.toEvent(): UserProfileFragmentEvent = this.map(
        onComplete = { UserProfileFragmentEvent.ProfileTask.Complete },
        onFailure = { e ->
            if (e.isByNetwork()) UserProfileFragmentEvent.ProfileTask.Failure.NoConnection
            else UserProfileFragmentEvent.ProfileTask.Failure.Irrecoverable
        }
    )

}