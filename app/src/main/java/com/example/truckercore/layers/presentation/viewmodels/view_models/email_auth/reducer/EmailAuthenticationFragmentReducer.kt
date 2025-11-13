package com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.reducer

import com.example.truckercore.layers.presentation.viewmodels.base._base.reducer.BaseReducer
import com.example.truckercore.layers.presentation.viewmodels.base._base.reducer.ReducerResult
import com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.effect.EmailAuthenticationFragmentEffect
import com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.event.EmailAuthenticationFragmentEvent
import com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.state.EmailAuthenticationFragmentState

class EmailAuthenticationFragmentReducer :
    BaseReducer<EmailAuthenticationFragmentEvent,
            EmailAuthenticationFragmentState,
            EmailAuthenticationFragmentEffect>() {

    override fun reduce(
        state: EmailAuthenticationFragmentState,
        event: EmailAuthenticationFragmentEvent
    ): ReducerResult<EmailAuthenticationFragmentState, EmailAuthenticationFragmentEffect> =
        when (event) {
            is EmailAuthenticationFragmentEvent.AuthenticationTask -> handleSystemEvent(event)
            is EmailAuthenticationFragmentEvent.Typing -> handleTypingEvent(event)
            is EmailAuthenticationFragmentEvent.Click -> handleClickEvent(event)
        }

}