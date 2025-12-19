package com.example.truckercore.layers.presentation.login.view_model.login.helpers

import com.example.truckercore.core.my_lib.classes.EmailCredential
import com.example.truckercore.layers.presentation.base.reducer.Reducer
import com.example.truckercore.layers.presentation.base.reducer.ReducerResult
import com.example.truckercore.layers.presentation.login.view.fragments.login.LoginFragment

/**
 * Reducer for the [LoginFragment] following the MVI pattern.
 *
 * The reducer is responsible for handling incoming [LoginFragmentEvent]s,
 * updating the [LoginFragmentState], and optionally emitting
 * [LoginFragmentEffect]s as one-time side effects.
 *
 * Each event type is handled separately in dedicated functions for clarity
 * and maintainability.
 */
class LoginFragmentReducer :
    Reducer<LoginFragmentEvent, LoginFragmentState, LoginFragmentEffect>() {

    override fun reduce(state: LoginFragmentState, event: LoginFragmentEvent) = when (event) {
        is LoginFragmentEvent.Click -> handleClickEvent(state, event)
        is LoginFragmentEvent.LoginTask -> handleLoginTaskEvent(state, event)
        is LoginFragmentEvent.Retry -> triggerLoginTaskAndUpdateState(state, event.credential)
        is LoginFragmentEvent.TextChange -> handleTextChangeEvent(state, event)
    }

    //----------------------------------------------------------------------------------------------
    // Handle Click Events
    //----------------------------------------------------------------------------------------------
    /**
     * Handles all click-related events from the UI (buttons, checkboxes).
     *
     * @param state Current state.
     * @param event Click event.
     */
    private fun handleClickEvent(
        state: LoginFragmentState,
        event: LoginFragmentEvent.Click
    ) = when (event) {

        is LoginFragmentEvent.Click.Checkbox -> {
            // Update the "Keep me logged in" preference
            val newEffect = LoginFragmentEffect.UpdatePreferences(event.isChecked)
            resultWithEffect(newEffect)
        }

        is LoginFragmentEvent.Click.Enter ->
            // Trigger login attempt with the provided credentials
            triggerLoginTaskAndUpdateState(state, event.credential)

        LoginFragmentEvent.Click.ForgetPassword -> {
            val effect = LoginFragmentEffect.Navigation.ToForgetPassword
            resultWithEffect(effect)
        }

        LoginFragmentEvent.Click.NewAccount -> {
            val effect = LoginFragmentEffect.Navigation.ToNewUser
            resultWithEffect(effect)
        }
    }

    /**
     * Attempts login and updates state, producing a side effect to launch the login task.
     *
     * @param state Current login fragment state.
     * @param credential Email and password for authentication.
     * @return Reducer result with updated state and effect to launch login.
     */
    private fun triggerLoginTaskAndUpdateState(
        state: LoginFragmentState,
        credential: EmailCredential
    ): ReducerResult<LoginFragmentState, LoginFragmentEffect> {
        val newState = state.tryToLogin()
        val newEffect = LoginFragmentEffect.LaunchLoginTask(credential)
        return resultWithStateAndEffect(newState, newEffect)
    }

    //----------------------------------------------------------------------------------------------
    // Handle Login Task Events
    //----------------------------------------------------------------------------------------------
    /**
     * Handles events from the login task, including success, failure,
     * invalid credentials, and no connection scenarios.
     *
     * @param state Current state.
     * @param event Login task event.
     * @return Reducer result with updated state or navigation effect.
     */
    private fun handleLoginTaskEvent(
        state: LoginFragmentState,
        event: LoginFragmentEvent.LoginTask
    ): ReducerResult<LoginFragmentState, LoginFragmentEffect> = when (event) {

        LoginFragmentEvent.LoginTask.Complete -> {
            val effect = LoginFragmentEffect.Navigation.ToCheckIn
            resultWithEffect(effect)
        }

        LoginFragmentEvent.LoginTask.Failure -> {
            val effect = LoginFragmentEffect.Navigation.ToNotification
            resultWithEffect(effect)
        }

        LoginFragmentEvent.LoginTask.InvalidCredential -> {
            val newState = state.invalidCredentials()
            resultWithState(newState)
        }

        LoginFragmentEvent.LoginTask.NoConnection -> {
            val effect = LoginFragmentEffect.Navigation.ToNoConnection
            resultWithEffect(effect)
        }
    }

    //----------------------------------------------------------------------------------------------
    // Handle Text Change Events
    //----------------------------------------------------------------------------------------------
    /**
     * Handles user input changes in the email and password fields,
     * performing validation and updating the state accordingly.
     *
     * @param state Current state.
     * @param event Text change event.
     * @return Reducer result with updated state.
     */
    private fun handleTextChangeEvent(
        state: LoginFragmentState,
        event: LoginFragmentEvent.TextChange
    ): ReducerResult<LoginFragmentState, LoginFragmentEffect> {
        val newState = when (event) {
            is LoginFragmentEvent.TextChange.Email -> state.updateEmail(event.text)
            is LoginFragmentEvent.TextChange.Password -> state.updatePassword(event.text)
        }
        return resultWithState(newState)
    }

}