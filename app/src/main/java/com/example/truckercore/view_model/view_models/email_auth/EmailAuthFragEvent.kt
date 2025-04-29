package com.example.truckercore.view_model.view_models.email_auth

/**
 * EmailAuthFragEvent represents user-driven UI events in the EmailAuthFragment.
 * These events are triggered by user interactions (e.g., button clicks) and are consumed by the ViewModel to update state or trigger logic.
 */
sealed class EmailAuthFragEvent {

    /**
     * Event triggered when the user clicks the "Create Account" button.
     * Initiates the registration flow by validating inputs and attempting to create a new user.
     */
    data object CreateAccountButtonClicked : EmailAuthFragEvent()

    /**
     * Event triggered when the user clicks the "Already Have an Account" button.
     * Typically leads to navigation to the login screen.
     */
    data object AlreadyHaveAccountButtonCLicked : EmailAuthFragEvent()
}