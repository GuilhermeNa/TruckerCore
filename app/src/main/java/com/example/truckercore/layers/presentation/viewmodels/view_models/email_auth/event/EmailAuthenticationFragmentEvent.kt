package com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.event

import com.example.truckercore.layers.presentation.viewmodels.base._contracts.Event

sealed class EmailAuthenticationFragmentEvent : Event {

    sealed class Click : EmailAuthenticationFragmentEvent() {
        data object ButtonCreate : Click()
        data object ButtonHaveAccount : Click()
        data object ImeActionDone : Click()
    }

    sealed class Typing : EmailAuthenticationFragmentEvent() {
        data class Email(val text: String) : Typing()
        data class Password(val text: String) : Typing()
        data class Confirmation(val text: String) : Typing()
    }

    sealed class AuthenticationTask : EmailAuthenticationFragmentEvent() {
        data object Complete : AuthenticationTask()
        sealed class Failure : AuthenticationTask() {
            data object NoConnection : Failure()
            data object InvalidCredentials : Failure()
            data object WeakPassword : Failure()
            data object UserCollision : Failure()
            data object Irrecoverable : Failure()
        }
    }

    data object RetryAuthentication : EmailAuthenticationFragmentEvent()

}