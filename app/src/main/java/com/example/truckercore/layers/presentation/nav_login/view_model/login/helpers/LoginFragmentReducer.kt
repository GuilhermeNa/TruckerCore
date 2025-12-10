package com.example.truckercore.layers.presentation.nav_login.view_model.login.helpers

import com.example.truckercore.core.my_lib.classes.EmailCredential
import com.example.truckercore.layers.presentation.base.reducer.Reducer
import com.example.truckercore.layers.presentation.base.reducer.ReducerResult

class LoginFragmentReducer : Reducer<LoginFragmentEvent, LoginFragmentState, LoginFragmentEffect>() {

    override fun reduce(
        state: LoginFragmentState,
        event: LoginFragmentEvent
    ) = when (event) {
        is LoginFragmentEvent.Click -> handleCLickEvent(event)
        is LoginFragmentEvent.LoginTask -> handleLoginTaskEvent(state, event)
        is LoginFragmentEvent.Retry -> triggerLoginTaskAndUpdateState(event.credential)
        is LoginFragmentEvent.TextChange -> handleTextChangeEvent(state, event)
    }

    //----------------------------------------------------------------------------------------------
    // Handle CLick Event
    //----------------------------------------------------------------------------------------------
    private fun handleCLickEvent(event: LoginFragmentEvent.Click) = when (event) {
        is LoginFragmentEvent.Click.Enter -> triggerLoginTaskAndUpdateState(event.credential)

        LoginFragmentEvent.Click.ForgetPassword -> {
            val effect = LoginFragmentEffect.Navigation.ToForgetPassword
            resultWithEffect(effect)
        }

        LoginFragmentEvent.Click.NewAccount -> {
            val effect = LoginFragmentEffect.Navigation.ToNewUser
            resultWithEffect(effect)
        }
    }

    private fun triggerLoginTaskAndUpdateState(credential: EmailCredential) =
        resultWithEffect(LoginFragmentEffect.LaunchLoginTask(credential))

    //----------------------------------------------------------------------------------------------
    // Handle Login Task Event
    //----------------------------------------------------------------------------------------------
    private fun handleLoginTaskEvent(
        state: LoginFragmentState,
        event: LoginFragmentEvent.LoginTask
    ): ReducerResult<LoginFragmentState, LoginFragmentEffect> {
        TODO("Not yet implemented")
    }


    private fun triggerNavigateToCheckIn(): ReducerResult<LoginFragmentState, LoginFragmentEffect> {
        val effect = LoginFragmentEffect.Navigation.ToCheckIn
        return resultWithEffect(effect)
    }

    private fun handleTextChangeEvent(
        state: LoginFragmentState,
        event: LoginFragmentEvent.TextChange
    ): ReducerResult<LoginFragmentState, LoginFragmentEffect> {
        val newState = when (event) {
            is LoginFragmentEvent.TextChange.Email ->  state.updateEmail(event.text)
            is LoginFragmentEvent.TextChange.Password -> state.updatePassword(event.text)
        }
        return resultWithState(newState)
    }

}