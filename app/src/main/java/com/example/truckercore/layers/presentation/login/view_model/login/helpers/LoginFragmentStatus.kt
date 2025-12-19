package com.example.truckercore.layers.presentation.login.view_model.login.helpers

/**
 * Represents the high-level status of the Login Fragment.
 *
 * This status tracks the overall interaction flow rather than individual
 * field-level validation errors. Each state reflects what the UI should
 * allow or display at a given moment.
 */
sealed class LoginFragmentStatus {

    /**
     * The user is still entering data.
     * Validation errors may be present and the login action is not yet allowed.
     */
    data object WaitingInput : LoginFragmentStatus()

    /**
     * All inputs are valid and the user can attempt to log in.
     */
    data object ReadyToLogin : LoginFragmentStatus()

    /**
     * A login attempt is in progress. UI should typically show a loading indicator
     * and block further input until the process completes.
     */
    data object TryingLogin : LoginFragmentStatus()

}