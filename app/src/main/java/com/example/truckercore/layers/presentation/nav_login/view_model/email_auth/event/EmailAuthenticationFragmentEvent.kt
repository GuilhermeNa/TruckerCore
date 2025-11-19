package com.example.truckercore.layers.presentation.nav_login.view_model.email_auth.event

import com.example.truckercore.layers.presentation.nav_login.fragments.email_auth.EmailAuthFragment
import com.example.truckercore.layers.presentation.base.contracts.Event

/**
 * Represents all possible events in the [EmailAuthFragment] screen.
 */
sealed class EmailAuthenticationFragmentEvent : Event {

    /** Click events triggered by user interactions. */
    sealed class Click : EmailAuthenticationFragmentEvent() {

        /** User tapped the "Create Account" button. */
        data object ButtonCreate : Click()

        /** User tapped the "I already have an account" button. */
        data object ButtonHaveAccount : Click()

        /** User pressed the "Done" action on the keyboard. */
        data object ImeActionDone : Click()

    }

    /** Typing events triggered by text input changes. */
    sealed class Typing : EmailAuthenticationFragmentEvent() {

        /** User typed in the email field. */
        data class Email(val text: String) : Typing()

        /** User typed in the password field. */
        data class Password(val text: String) : Typing()

        /** User typed in the confirmation field. */
        data class Confirmation(val text: String) : Typing()

    }

    /** Authentication task events representing success or failure. */
    sealed class AuthenticationTask : EmailAuthenticationFragmentEvent() {
        /** Authentication was successful. */
        data object Complete : AuthenticationTask()

        /** Authentication failed. */
        sealed class Failure : AuthenticationTask() {

            /** Device has no internet connection. */
            data object NoConnection : Failure()

            /** Email or password is incorrect. */
            data object InvalidCredentials : Failure()

            /** Password does not meet security requirements. */
            data object WeakPassword : Failure()

            /** Email is already registered. */
            data object UserCollision : Failure()

            /** An unknown or unrecoverable error occurred. */
            data object Irrecoverable : Failure()

        }

    }

    /** User requested to retry authentication after a connection failure. */
    data object RetryAuthentication : EmailAuthenticationFragmentEvent()

}