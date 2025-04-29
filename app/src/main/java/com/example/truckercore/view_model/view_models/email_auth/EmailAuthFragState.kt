package com.example.truckercore.view_model.view_models.email_auth

/**
 * EmailAuthFragState represents the UI state of the EmailAuthFragment.
 * It models the various stages the screen can be in, from initial input waiting to success or error states.
 */
sealed class EmailAuthFragState {

    /**
     * UI is waiting for user input. Default idle state before any action is taken.
     */
    data object WaitingInput : EmailAuthFragState()

    /**
     * User has submitted data and account creation is in progress.
     * UI should reflect a loading or processing state.
     */
    data object Creating : EmailAuthFragState()

    /**
     * User account was successfully created.
     * Typically results in a transition to the next screen in the flow.
     */
    data object Success : EmailAuthFragState()

    /**
     * An error occurred during user input validation.
     *
     * @property validationResult An object containing specific input field errors with messages,
     * allowing the UI to display appropriate feedback.
     */
    data class UserInputError(
        val validationResult: EmailAuthUserInputValidationResult
    ) : EmailAuthFragState()

}