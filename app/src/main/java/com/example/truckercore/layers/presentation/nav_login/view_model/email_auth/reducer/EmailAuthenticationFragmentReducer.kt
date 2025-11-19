package com.example.truckercore.layers.presentation.nav_login.view_model.email_auth.reducer

import com.example.truckercore.layers.presentation.nav_login.fragments.email_auth.EmailAuthFragment
import com.example.truckercore.layers.presentation.base.reducer.Reducer
import com.example.truckercore.layers.presentation.base.reducer.ReducerResult
import com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.effect.EmailAuthenticationFragmentEffect
import com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.event.EmailAuthenticationFragmentEvent
import com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.state.EmailAuthenticationFragmentState

/**
 * Reducer responsible for managing all state transitions and one-time effects
 * associated with the Email [EmailAuthFragment].
 *
 * This reducer transform user-generated events and backend results into predictable and
 * immutable UI states. It guarantees a unidirectional flow, ensuring consistent UI
 * behavior and eliminating side effects from business logic.
 */
class EmailAuthenticationFragmentReducer :
    Reducer<EmailAuthenticationFragmentEvent,
            EmailAuthenticationFragmentState, EmailAuthenticationFragmentEffect>() {

    override fun reduce(
        state: EmailAuthenticationFragmentState,
        event: EmailAuthenticationFragmentEvent
    ): ReducerResult<EmailAuthenticationFragmentState, EmailAuthenticationFragmentEffect> =
        when (event) {
            is EmailAuthenticationFragmentEvent.Typing ->
                handleTypingEvent(state, event)

            is EmailAuthenticationFragmentEvent.Click ->
                handleClickEvent(state, event)

            is EmailAuthenticationFragmentEvent.AuthenticationTask ->
                handleAuthTaskEvent(state, event)

            EmailAuthenticationFragmentEvent.RetryAuthentication ->
                triggerAuthenticationTaskEffectAndUpdateState(state)
        }

    //----------------------------------------------------------------------------------------------
    // Typing Events
    //----------------------------------------------------------------------------------------------
    /**
     * Handles all user typing interactions such as updating:
     * - Email field
     * - Password field
     * - Confirmation password field
     *
     * @param state Current UI state.
     * @param event User typing event in a specific input field.
     */
    private fun handleTypingEvent(
        state: EmailAuthenticationFragmentState,
        event: EmailAuthenticationFragmentEvent.Typing
    ): ReducerResult<EmailAuthenticationFragmentState, EmailAuthenticationFragmentEffect> =
        when (event) {
            is EmailAuthenticationFragmentEvent.Typing.Email ->
                updateEmailComponent(state, event.text)

            is EmailAuthenticationFragmentEvent.Typing.Password ->
                updatePasswordComponent(state, event.text)

            is EmailAuthenticationFragmentEvent.Typing.Confirmation ->
                updateConfirmationComponent(state, event.text)
        }

    private fun updateEmailComponent(state: EmailAuthenticationFragmentState, text: String)
            : ReducerResult<EmailAuthenticationFragmentState, EmailAuthenticationFragmentEffect> {
        val newState = state.updateEmail(text)
        return resultWithState(newState)
    }

    private fun updatePasswordComponent(state: EmailAuthenticationFragmentState, text: String)
            : ReducerResult<EmailAuthenticationFragmentState, EmailAuthenticationFragmentEffect> {
        val newState = state.updatePassword(text)
        return resultWithState(newState)
    }

    private fun updateConfirmationComponent(state: EmailAuthenticationFragmentState, text: String)
            : ReducerResult<EmailAuthenticationFragmentState, EmailAuthenticationFragmentEffect> {
        val newState = state.updateConfirmation(text)
        return resultWithState(newState)
    }

    //----------------------------------------------------------------------------------------------
    // Click Events
    //----------------------------------------------------------------------------------------------
    /**
     * Handles UI click events such as:
     * - Create account button
     * - Already have an account button
     * - IME "Done" action
     *
     * @param state Current UI state.
     * @param event Click event representing user action.
     */
    private fun handleClickEvent(
        state: EmailAuthenticationFragmentState,
        event: EmailAuthenticationFragmentEvent.Click
    ): ReducerResult<EmailAuthenticationFragmentState, EmailAuthenticationFragmentEffect> =
        when (event) {
            EmailAuthenticationFragmentEvent.Click.ButtonCreate ->
                triggerAuthenticationTaskEffectAndUpdateState(state)

            EmailAuthenticationFragmentEvent.Click.ButtonHaveAccount ->
                triggerNavigateToLoginEffect()

            EmailAuthenticationFragmentEvent.Click.ImeActionDone ->
                triggerAuthenticationTaskEffectAndUpdateState(state)
        }

    private fun triggerAuthenticationTaskEffectAndUpdateState(state: EmailAuthenticationFragmentState)
            : ReducerResult<EmailAuthenticationFragmentState, EmailAuthenticationFragmentEffect> {
        val newState = state.creating()
        val newEffect = EmailAuthenticationFragmentEffect.Task.AuthenticateEmail
        return resultWithStateAndEffect(newState, newEffect)
    }

    private fun triggerNavigateToLoginEffect()
            : ReducerResult<EmailAuthenticationFragmentState, EmailAuthenticationFragmentEffect> {
        val newEffect = EmailAuthenticationFragmentEffect.Navigation.ToLogin
        return resultWithEffect(newEffect)
    }

    //----------------------------------------------------------------------------------------------
    // Authentication Task Events
    //----------------------------------------------------------------------------------------------

    /**
     * Handles the different results produced by the authentication attempt:
     * - Complete
     * - Failure (Irrecoverable, No Connection, User Collision, Invalid Credentials, Weak Password)
     *
     * @param state Current UI state.
     * @param event Outcome of the authentication task.
     */
    private fun handleAuthTaskEvent(
        state: EmailAuthenticationFragmentState,
        event: EmailAuthenticationFragmentEvent.AuthenticationTask
    ): ReducerResult<EmailAuthenticationFragmentState, EmailAuthenticationFragmentEffect> =
        when (event) {
            EmailAuthenticationFragmentEvent.AuthenticationTask.Complete ->
                triggerNavigateToVerifyEmailEffect()

            EmailAuthenticationFragmentEvent.AuthenticationTask.Failure.Irrecoverable ->
                triggerNavigateToErrorActivityEffect()

            EmailAuthenticationFragmentEvent.AuthenticationTask.Failure.NoConnection ->
                triggerNavigateToNoConnectionEffectAndUpdateState(state)

            EmailAuthenticationFragmentEvent.AuthenticationTask.Failure.UserCollision ->
                triggerWarningUserCollision(state)

            EmailAuthenticationFragmentEvent.AuthenticationTask.Failure.InvalidCredentials ->
                triggerWarningInvalidCredentials(state)

            EmailAuthenticationFragmentEvent.AuthenticationTask.Failure.WeakPassword ->
                triggerNavigateToErrorActivityEffect()
        }

    private fun triggerNavigateToVerifyEmailEffect()
            : ReducerResult<EmailAuthenticationFragmentState, EmailAuthenticationFragmentEffect> {
        val newEffect = EmailAuthenticationFragmentEffect.Navigation.ToVerifyEmail
        return resultWithEffect(newEffect)
    }

    private fun triggerNavigateToErrorActivityEffect()
            : ReducerResult<EmailAuthenticationFragmentState, EmailAuthenticationFragmentEffect> {
        val newEffect = EmailAuthenticationFragmentEffect.Navigation.ToNotification
        return resultWithEffect(newEffect)
    }

    private fun triggerNavigateToNoConnectionEffectAndUpdateState(
        state: EmailAuthenticationFragmentState
    ): ReducerResult<EmailAuthenticationFragmentState, EmailAuthenticationFragmentEffect> {
        val newState = state.readyToCreate()
        val newEffect = EmailAuthenticationFragmentEffect.Navigation.ToNoConnection
        return resultWithStateAndEffect(newState, newEffect)
    }

    private fun triggerWarningUserCollision(state: EmailAuthenticationFragmentState)
            : ReducerResult<EmailAuthenticationFragmentState, EmailAuthenticationFragmentEffect> {
        val newState = state.warnUserCollision()
        val newEffect = EmailAuthenticationFragmentEffect.WarningToast(USER_COLLISION)
        return resultWithStateAndEffect(newState, newEffect)
    }

    private fun triggerWarningInvalidCredentials(state: EmailAuthenticationFragmentState)
            : ReducerResult<EmailAuthenticationFragmentState, EmailAuthenticationFragmentEffect> {
        val newState = state.waitingInput()
        val newEffect = EmailAuthenticationFragmentEffect.WarningToast(INVALID_CREDENTIALS)
        return resultWithStateAndEffect(newState, newEffect)
    }

    private companion object {

        private const val INVALID_CREDENTIALS =
            "Credenciais inválidas. Verifique seu e-mail e senha."

        private const val USER_COLLISION =
            "Já existe uma conta cadastrada com este e-mail."

    }

}