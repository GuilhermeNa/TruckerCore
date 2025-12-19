package com.example.truckercore.layers.presentation.login.view_model.continue_register.helpers

import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.layers.domain.base.enums.RegistrationStatus
import com.example.truckercore.layers.presentation.login.fragments.continue_register.ContinueRegisterFragment
import com.example.truckercore.layers.presentation.base.reducer.Reducer
import com.example.truckercore.layers.presentation.base.reducer.ReducerResult

/**
 * Reducer responsible for managing UI events and updating the [ContinueRegisterFragmentState].
 *
 * It receives UI events represented by [ContinueRegisterFragmentEvent],
 * and based on those, determines whether the [ContinueRegisterFragmentState]
 * should change and/or a [ContinueRegisterFragmentEffect] should be triggered.
 *
 * @see ContinueRegisterFragmentEvent For the list of possible events.
 * @see ContinueRegisterFragmentState For the current UI state representation.
 * @see ContinueRegisterFragmentEffect For the possible side effects (navigation, error screens, etc.).
 */
class ContinueRegisterFragmentReducer :
    Reducer<ContinueRegisterFragmentEvent, ContinueRegisterFragmentState,
            ContinueRegisterFragmentEffect>() {

    /**
     * Defines how the state and effects should change based on the received event.
     *
     * @param state The current UI state.
     * @param event The incoming event (e.g., button click, task result).
     * @return A [ReducerResult] containing the updated state and/or triggered effect.
     */
    override fun reduce(
        state: ContinueRegisterFragmentState,
        event: ContinueRegisterFragmentEvent
    ): ReducerResult<ContinueRegisterFragmentState, ContinueRegisterFragmentEffect> =
        when (event) {
            is ContinueRegisterFragmentEvent.Click -> handleClickEvent(state, event)
            is ContinueRegisterFragmentEvent.CheckRegisterTask -> handleTaskEvent(state, event)
        }

    //----------------------------------------------------------------------------------------------
    // Click Events
    //----------------------------------------------------------------------------------------------
    /**
     * Handles click-related events in the [ContinueRegisterFragment].
     * Depending on the interaction, the state remains the same but navigation
     * effects may be triggered.
     *
     * @param state The current UI state.
     * @param event The click event being processed.
     * @return A [ReducerResult] containing any triggered effect.
     */
    private fun handleClickEvent(
        state: ContinueRegisterFragmentState,
        event: ContinueRegisterFragmentEvent.Click
    ): ReducerResult<ContinueRegisterFragmentState, ContinueRegisterFragmentEffect> =
        when (event) {

            // Triggered when the user wants to proceed to the next registration step.
            ContinueRegisterFragmentEvent.Click.ContinueRegisterButton ->
                triggerNavigateToNextRegistrationStep(state)

            // Triggered when the user opts to restart the registration process.
            ContinueRegisterFragmentEvent.Click.NewRegisterButton ->
                triggerNavigateToEmailAuthEffect()

        }

    /**
     * Determines which next screen the user should be navigated to,
     * interpreted dynamically based on the current screen state.
     *
     * If the state indicates [ContinueRegisterFragmentStatus.EmailNotVerified], the next destination will be
     * the fragment responsible for handling email verification.
     *
     * If the state indicates [ContinueRegisterFragmentStatus.MissingProfile], navigation will proceed to the
     * fragment responsible for completing the user profile setup.
     *
     * @param state The current UI state.
     * @return A [ReducerResult] containing the triggered navigation effect.
     */
    private fun triggerNavigateToNextRegistrationStep(
        state: ContinueRegisterFragmentState
    ): ReducerResult<ContinueRegisterFragmentState, ContinueRegisterFragmentEffect> {
        val newEffect = if (state.isEmailNotVerified()) {
            ContinueRegisterFragmentEffect.Navigation.ToVerifyEmail
        } else {
            ContinueRegisterFragmentEffect.Navigation.ToUserName
        }
        return resultWithEffect(newEffect)
    }

    /**
     * Triggers navigation back to the Email Authentication step,
     * typically when the user chooses to start over and register a new [Email].
     *
     * @return A [ReducerResult] containing the triggered effect.
     */
    private fun triggerNavigateToEmailAuthEffect()
            : ReducerResult<ContinueRegisterFragmentState, ContinueRegisterFragmentEffect> {
        val newEffect = ContinueRegisterFragmentEffect.Navigation.ToEmailAuth
        return resultWithEffect(newEffect)
    }

    //----------------------------------------------------------------------------------------------
    // Task Events
    //----------------------------------------------------------------------------------------------
    /**
     * Handles events related to background registration checks.
     * Depending on the task result, the state may be updated
     * or navigation to error-related screens may be triggered.
     *
     * @param state The current UI state.
     * @param event The task result event.
     * @return A [ReducerResult] containing the updated state and/or triggered effect.
     */
    private fun handleTaskEvent(
        state: ContinueRegisterFragmentState,
        event: ContinueRegisterFragmentEvent.CheckRegisterTask
    ): ReducerResult<ContinueRegisterFragmentState, ContinueRegisterFragmentEffect> =
        when (event) {

            // Triggered when the registration check completes successfully.
            is ContinueRegisterFragmentEvent.CheckRegisterTask.Complete ->
                updateStatusByTaskResult(event, state)

            // Triggered when the request fails due to connectivity issues.
            ContinueRegisterFragmentEvent.CheckRegisterTask.Failure.NoConnection ->
                triggerNavigateToNoConnection()

            // Triggered when the request fails due to an unrecoverable error.
            ContinueRegisterFragmentEvent.CheckRegisterTask.Failure.Irrecoverable ->
                triggerNavigateToErrorActivityEffect()
        }

    /**
     * Updates the UI state based on the result of the registration status check.
     *
     * @param event The completed task event containing registration status data.
     * @param state The current UI state.
     * @return A [ReducerResult] containing the updated state.
     */
    private fun updateStatusByTaskResult(
        event: ContinueRegisterFragmentEvent.CheckRegisterTask.Complete,
        state: ContinueRegisterFragmentState
    ): ReducerResult<ContinueRegisterFragmentState, ContinueRegisterFragmentEffect> {
        val registration = event.registrationStatus
        val email = event.email

        val newState = when (registration) {
            RegistrationStatus.COMPLETE, RegistrationStatus.ACCOUNT_NOT_FOUND ->
                throw IllegalArgumentException(ERROR_MSG + registration)

            RegistrationStatus.EMAIL_NOT_VERIFIED -> state.emailNotVerified(email)

            RegistrationStatus.MISSING_PROFILE -> state.missingProfile(email)
        }

        return resultWithState(newState)
    }

    /**
     * Triggers navigation to a “No Connection” screen,
     * shown when the registration task fails due to lack of internet connectivity.
     *
     * @return A [ReducerResult] containing the triggered effect.
     */
    private fun triggerNavigateToNoConnection()
            : ReducerResult<ContinueRegisterFragmentState, ContinueRegisterFragmentEffect> {
        val newEffect = ContinueRegisterFragmentEffect.Navigation.ToNoConnection
        return resultWithEffect(newEffect)
    }

    /**
     * Triggers navigation to a generic error screen,
     * typically used when the registration check fails irrecoverably.
     *
     * @return A [ReducerResult] containing the triggered effect.
     */
    private fun triggerNavigateToErrorActivityEffect()
            : ReducerResult<ContinueRegisterFragmentState, ContinueRegisterFragmentEffect> {
        val newEffect = ContinueRegisterFragmentEffect.Navigation.ToNotification
        return resultWithEffect(newEffect)
    }

    companion object {
        private const val ERROR_MSG =
            "Illegal argument received in ContinueRegisterFragmentReducer" +
                    " while trying to update the fragment status. Received: "
    }

}