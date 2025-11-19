package com.example.truckercore.layers.presentation.nav_login.view_model.user_name.reducer

import com.example.truckercore.layers.presentation.base.reducer.Reducer
import com.example.truckercore.layers.presentation.base.reducer.ReducerResult
import com.example.truckercore.layers.presentation.viewmodels.view_models.user_name.effect.UserProfileFragmentEffect
import com.example.truckercore.layers.presentation.viewmodels.view_models.user_name.event.UserProfileFragmentEvent
import com.example.truckercore.layers.presentation.viewmodels.view_models.user_name.state.UserProfileFragmentState

/**
 * Reducer responsible for transforming incoming [UserProfileFragmentEvent]s into:
 * - Updated [UserProfileFragmentState] instances, and/or
 * - One-time [UserProfileFragmentEffect] instructions for navigation or UI actions.
 *
 * This class implements the core state machine for the user profile screen:
 * - Validates input changes.
 * - Handles the creation flow and its outcomes.
 * - Triggers navigation when the user is not logged in or when profile creation succeeds.
 */
class UserProfileFragmentReducer :
    Reducer<UserProfileFragmentEvent, UserProfileFragmentState, UserProfileFragmentEffect>() {

    override fun reduce(
        state: UserProfileFragmentState,
        event: UserProfileFragmentEvent
    ): ReducerResult<UserProfileFragmentState, UserProfileFragmentEffect> =
        when (event) {

            UserProfileFragmentEvent.UserNotLogged ->
                triggerNavigateToLoginEffectAndWarnUser()

            is UserProfileFragmentEvent.FabClicked ->
                triggerProfileTaskAndUpdateState(state)

            is UserProfileFragmentEvent.ProfileTask ->
                handleProfileTask(state, event)

            is UserProfileFragmentEvent.TextChanged ->
                updateNameComponent(state, event)

            UserProfileFragmentEvent.Retry ->
                triggerProfileTaskAndUpdateState(state)
        }

    /**
     * Creates a result that triggers navigation to the login screen when the user
     * is not authenticated. No state update is needed.
     */
    private fun triggerNavigateToLoginEffectAndWarnUser():
            ReducerResult<UserProfileFragmentState, UserProfileFragmentEffect> {

        val newEffect = UserProfileFragmentEffect.Navigation.ToLogin
        return resultWithEffect(newEffect)
    }

    /**
     * Transitions the UI into the "creating" state and triggers the execution
     * of the profile creation task handled by the ViewModel.
     *
     * @param state The current fragment state prior to creating the profile.
     */
    private fun triggerProfileTaskAndUpdateState(
        state: UserProfileFragmentState
    ): ReducerResult<UserProfileFragmentState, UserProfileFragmentEffect> {

        val newState = state.creating()
        val newEffect = UserProfileFragmentEffect.ProfileTask
        return resultWithStateAndEffect(newState, newEffect)
    }

    /**
     * Handles all subtypes of [UserProfileFragmentEvent.ProfileTask], mapping them
     * to appropriate state transitions and UI effects.
     *
     * @param state The current UI state.
     * @param event The outcome of the profile creation task.
     */
    private fun handleProfileTask(
        state: UserProfileFragmentState,
        event: UserProfileFragmentEvent.ProfileTask
    ): ReducerResult<UserProfileFragmentState, UserProfileFragmentEffect> =
        when (event) {

            UserProfileFragmentEvent.ProfileTask.Complete -> {
                val newEffect = UserProfileFragmentEffect.Navigation.ToCheckIn
                resultWithEffect(newEffect)
            }

            UserProfileFragmentEvent.ProfileTask.Failure.NoConnection -> {
                val newState = state.readyToCreate()
                val newEffect = UserProfileFragmentEffect.Navigation.ToNoConnection
                resultWithStateAndEffect(newState, newEffect)
            }

            UserProfileFragmentEvent.ProfileTask.Failure.Irrecoverable -> {
                val newEffect = UserProfileFragmentEffect.Navigation.ToNotification
                resultWithEffect(newEffect)
            }
        }

    /**
     * Updates the name input component within the state whenever the user changes the text field.
     *
     * @param state The current UI state.
     * @param event The event containing the updated text.
     */
    private fun updateNameComponent(
        state: UserProfileFragmentState,
        event: UserProfileFragmentEvent.TextChanged
    ): ReducerResult<UserProfileFragmentState, UserProfileFragmentEffect> {

        val newState = state.updateName(event.text)
        return resultWithState(newState)
    }

}