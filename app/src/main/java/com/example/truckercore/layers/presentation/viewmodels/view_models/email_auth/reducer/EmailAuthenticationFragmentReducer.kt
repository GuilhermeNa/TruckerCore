package com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.reducer

import com.example.truckercore.layers.presentation.viewmodels.base._base.reducer.BaseReducer
import com.example.truckercore.layers.presentation.viewmodels.base._base.reducer.ReducerResult
import com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.effect.EmailAuthenticationFragmentEffect
import com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.event.EmailAuthenticationFragmentEvent
import com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.state.EmailAuthenticationFragmentState

class EmailAuthenticationFragmentReducer :
    BaseReducer<EmailAuthenticationFragmentEvent, EmailAuthenticationFragmentState, EmailAuthenticationFragmentEffect>() {

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
    private fun handleAuthTaskEvent(
        state: EmailAuthenticationFragmentState,
        event: EmailAuthenticationFragmentEvent.AuthenticationTask
    ): ReducerResult<EmailAuthenticationFragmentState, EmailAuthenticationFragmentEffect> =
        when (event) {
            EmailAuthenticationFragmentEvent.AuthenticationTask.Complete ->
                triggerNavigateToVerifyEmailEffectAndUpdateState(state)

            EmailAuthenticationFragmentEvent.AuthenticationTask.Failure.Irrecoverable ->
                triggerNavigateToErrorActivityEffect()

            EmailAuthenticationFragmentEvent.AuthenticationTask.Failure.NoConnection ->
                triggerNavigateToNoConnectionEffectAndUpdateState(state)

            EmailAuthenticationFragmentEvent.AuthenticationTask.Failure.InvalidCredentials ->
                triggerNWarningToastEffectAndUpdateState(state, INVALID_CREDENTIALS)

            EmailAuthenticationFragmentEvent.AuthenticationTask.Failure.WeakPassword ->
                triggerNWarningToastEffectAndUpdateState(state, WEAK_PASSWORD)

            EmailAuthenticationFragmentEvent.AuthenticationTask.Failure.UserCollision ->
                triggerNWarningToastEffectAndUpdateState(state, USER_COLLISION)
        }

    private fun triggerNavigateToVerifyEmailEffectAndUpdateState(
        state: EmailAuthenticationFragmentState
    ): ReducerResult<EmailAuthenticationFragmentState, EmailAuthenticationFragmentEffect> {
        val newState = state.created()
        val newEffect = EmailAuthenticationFragmentEffect.Navigation.ToVerifyEmail
        return resultWithStateAndEffect(newState, newEffect)
    }

    private fun triggerNavigateToErrorActivityEffect()
            : ReducerResult<EmailAuthenticationFragmentState, EmailAuthenticationFragmentEffect> {
        val newEffect = EmailAuthenticationFragmentEffect.Navigation.ToNotification
        return resultWithEffect(newEffect)
    }

    private fun triggerNavigateToNoConnectionEffectAndUpdateState(
        state: EmailAuthenticationFragmentState
    ): ReducerResult<EmailAuthenticationFragmentState, EmailAuthenticationFragmentEffect> {
        val newState = state.waitingInput()
        val newEffect = EmailAuthenticationFragmentEffect.Navigation.ToNoConnection
        return resultWithStateAndEffect(newState, newEffect)
    }

    private fun triggerNWarningToastEffectAndUpdateState(
        state: EmailAuthenticationFragmentState,
        text: String
    ): ReducerResult<EmailAuthenticationFragmentState, EmailAuthenticationFragmentEffect> {
        val newState = state.waitingInput()
        val newEffect = EmailAuthenticationFragmentEffect.WarningToast(text)
        return resultWithStateAndEffect(newState, newEffect)
    }

    private companion object {
        private const val INVALID_CREDENTIALS =
            "Credenciais inválidas. Verifique seu e-mail e senha."
        private const val WEAK_PASSWORD =
            "A senha informada é muito fraca. Tente uma senha mais forte."
        private const val USER_COLLISION = "Já existe uma conta cadastrada com este e-mail."
    }

}