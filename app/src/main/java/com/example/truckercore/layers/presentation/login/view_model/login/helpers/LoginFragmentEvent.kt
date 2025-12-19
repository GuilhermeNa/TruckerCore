package com.example.truckercore.layers.presentation.login.view_model.login.helpers

import com.example.truckercore.core.my_lib.classes.EmailCredential
import com.example.truckercore.layers.presentation.base.contracts.Event
import com.example.truckercore.layers.presentation.login.view.fragments.login.LoginFragment
import com.example.truckercore.layers.presentation.login.view_model.login.LoginViewModel

/**
 * Represents all user-driven and system-driven events that can occur
 * within the [LoginFragment]. These events are consumed by the [LoginViewModel]
 * and processed by the [LoginFragmentReducer] to update state or trigger effects.
 *
 * This sealed class hierarchy defines a strongly typed event system
 * used in an MVI / UDF architecture, ensuring exhaustive event handling.
 */
sealed class LoginFragmentEvent : Event {

    /**
     * Triggered when the user attempts to retry the login process.
     * Typically dispatched after a connection error or when attempting
     * a manual login flow.
     */
    data class Retry(val credential: EmailCredential) : LoginFragmentEvent()

    //----------------------------------------------------------------------------------------------
    // Text Change Events
    //----------------------------------------------------------------------------------------------
    /**
     * Represents real-time user input changes for form fields.
     * These events allow the ViewModel to validate the input incrementally.
     */
    sealed class TextChange : LoginFragmentEvent() {

        /** Fired when the email input field changes. */
        data class Email(val text: String) : TextChange()

        /** Fired when the password input field changes. */
        data class Password(val text: String) : TextChange()
    }

    //----------------------------------------------------------------------------------------------
    // Click Events
    //----------------------------------------------------------------------------------------------
    /**
     * Represents all click or tap actions performed by the user.
     */
    sealed class Click : LoginFragmentEvent() {

        /** User toggled the "keep me logged in" checkbox. */
        data class Checkbox(val isChecked: Boolean) : Click()

        /** User pressed the Login/Enter button with the given credentials. */
        data class Enter(val credential: EmailCredential) : Click()

        /** User requested to navigate to the new-account registration flow. */
        data object NewAccount : Click()

        /** User requested to navigate to the password recovery flow. */
        data object ForgetPassword : Click()
    }

    //----------------------------------------------------------------------------------------------
    // Login Task Events (asynchronous outcomes)
    //----------------------------------------------------------------------------------------------
    /**
     * Represents the result of an asynchronous login operation.
     * These events are typically emitted after executing the SignInUseCase.
     */
    sealed class LoginTask : LoginFragmentEvent() {

        /** Login completed successfully. */
        data object Complete : LoginTask()

        /** General login failure (not specific to connectivity or credentials). */
        data object Failure : LoginTask()

        /** Login failed due to connectivity issues. */
        data object NoConnection : LoginTask()

        /** Login failed due to incorrect or invalid user credentials. */
        data object InvalidCredential : LoginTask()
    }

}