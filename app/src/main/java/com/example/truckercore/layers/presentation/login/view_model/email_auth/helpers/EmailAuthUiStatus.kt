package com.example.truckercore.layers.presentation.login.view_model.email_auth.helpers

/**
 * Represents the high-level UI status for the [EmailAuthFragment] screen.
 *
 * This sealed class models the different phases of user interaction during the
 * account creation flow, ensuring a predictable and type-safe representation of
 * UI state transitions. Each state conveys the current capabilities or restrictions
 * of the screen and is used by the View or State Handler to render appropriate UI
 * feedback (buttons enabled/disabled, loading indicators, etc.).
 *
 * #### UI States
 *
 * • [WaitingInput]
 *      Description The default state. User has not fully completed the required fields
 *      or the input is not yet valid to proceed. The "Create Account" button should be disabled.
 *
 * • [ReadyToCreate]
 *      All fields are valid and the user is allowed to attempt account creation.
 *      The "Create Account" button should be enabled.
 *
 * • [Creating]
 *      The authentication task is in progress. UI should typically display
 *      a loading indicator and prevent additional input or clicks.
 *      The "Create Account" button should be enabled.
 */
sealed class EmailAuthUiStatus {

    /**
     * Indicates that the UI is waiting for valid input from the user.
     */
    data object WaitingInput : EmailAuthUiStatus()

    /**
     * Indicates that all input fields are valid and the UI is ready to
     * trigger the account creation process.
     */
    data object ReadyToCreate : EmailAuthUiStatus()

    /**
     * Indicates that an authentication attempt is currently in progress.
     * UI elements should reflect a loading state at this point.
     */
    data object Creating : EmailAuthUiStatus()

    /**
     * @return true if the current state represents the "Creating" phase,
     *         meaning the authentication task is running.
     */
    fun isCreating() = this is Creating

    /**
     * @return true if the current state allows the user to initiate
     *         the account creation process.
     */
    fun isReadyToCreate() = this is ReadyToCreate

}