package com.example.truckercore.layers.presentation.nav_login.view_model.user_name.event

import com.example.truckercore.layers.presentation.base.contracts.Event

/**
 * Represents all possible UI-driven or internally triggered events
 * that can occur within the user profile creation screen.
 *
 * These events are consumed by the [UserProfileFragmentReducer] and may lead to:
 * - State transitions,
 * - Execution of tasks (e.g., creating a user profile),
 * - Emission of navigation effects,
 * - Validation updates tied to user input.
 */
sealed class UserProfileFragmentEvent : Event {

    /**
     * Event emitted whenever the user modifies the name input field.
     *
     * @param text The current text typed by the user.
     */
    data class TextChanged(val text: String) : UserProfileFragmentEvent()

    /**
     * Triggered when the FAB is clicked, typically initiating profile creation
     * if the input is valid.
     */
    data object FabClicked : UserProfileFragmentEvent()

    /**
     * Emitted when the ViewModel determines that no logged-in user is present.
     * Usually triggers navigation to the login screen.
     */
    data object UserNotLogged : UserProfileFragmentEvent()

    /**
     * Event used to retry operations that previously failed,
     * usually following a no-connection scenario.
     */
    data object Retry : UserProfileFragmentEvent()

    /**
     * Represents events related to the execution and result of the profile creation task.
     */
    sealed class ProfileTask : UserProfileFragmentEvent() {

        /**
         * Indicates that the user profile was successfully created.
         */
        data object Complete : ProfileTask()

        /**
         * Represents all failure cases for the profile creation workflow.
         */
        sealed class Failure : ProfileTask() {

            /**
             * A recoverable failure caused by network issues.
             * Typically followed by navigation to a "no connection" screen.
             */
            data object NoConnection : Failure()

            /**
             * An unrecoverable failure indicating that the operation cannot proceed.
             * Often results in navigation to a generic error or notification screen.
             */
            data object Irrecoverable : Failure()
        }
    }

}