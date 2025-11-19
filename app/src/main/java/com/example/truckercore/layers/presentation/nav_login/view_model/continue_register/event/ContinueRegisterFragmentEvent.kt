package com.example.truckercore.layers.presentation.nav_login.view_model.continue_register.event

import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.layers.domain.base.enums.RegistrationStatus
import com.example.truckercore.layers.presentation.nav_login.fragments.continue_register.ContinueRegisterFragment
import com.example.truckercore.layers.presentation.base.contracts.Event
import com.example.truckercore.layers.presentation.viewmodels.view_models.continue_register.reducer.ContinueRegisterFragmentReducer

/**
 * Represents all UI-driven and task-driven events that can occur
 * in the [ContinueRegisterFragment].
 *
 * Events are processed by [ContinueRegisterFragmentReducer], which interprets them
 * to update the current UI state or trigger navigation/effects.
 */
sealed class ContinueRegisterFragmentEvent : Event {

    /**
     * Click-related events triggered by user interaction with UI components
     * such as buttons displayed on the Continue Register screen.
     */
    sealed class Click : ContinueRegisterFragmentEvent() {

        /**
         * Triggered when the user chooses to proceed with the registration flow.
         *
         * This typically advances the user to the next required step,
         * depending on their registration status.
         */
        data object ContinueRegisterButton : Click()

        /**
         * Triggered when the user decides to restart the registration process.
         *
         * This usually navigates the user back to the initial email authentication screen.
         */
        data object NewRegisterButton : Click()
    }

    /**
     * Events emitted as a result of background operations that check
     * the user's registration progress and associated email.
     *
     * These events indicate whether the operation succeeded or failed,
     * allowing the UI to react accordingly.
     */
    sealed class CheckRegisterTask : ContinueRegisterFragmentEvent() {

        /**
         * Emitted when the registration verification task completes successfully.
         *
         * @property registrationStatus The current registration status retrieved from the backend.
         * @property email The user’s email associated with this registration check.
         */
        data class Complete(
            val registrationStatus: RegistrationStatus,
            val email: Email
        ) : CheckRegisterTask()

        /**
         * Represents different types of failures that may occur during
         * the registration verification process.
         */
        sealed class Failure : CheckRegisterTask() {
            // Represents a lack of connection
            data object NoConnection : Failure()

            // App should be close
            data object Irrecoverable : Failure()
        }
    }

}