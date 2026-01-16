package com.example.truckercore.layers.presentation.login.view_model.continue_register.helpers

/**
 * Represents the different UI states of the [ContinueRegisterFragment].
 *
 * Each state indicates a specific point in the user’s registration flow,
 * allowing the UI and reducer to react appropriately based on the user's progress.
 */
sealed class ContinueRegisterFragmentStatus {

    /**
     * Indicates that the screen is still loading registration information.
     */
    data object Loading : ContinueRegisterFragmentStatus()

    /**
     * Indicates that the information have been loaded and user's email has not yet been verified.
     */
    data object EmailNotVerified : ContinueRegisterFragmentStatus()

    /**
     * Indicates that the information have been loaded,
     * email have been verified but the profile is missing.
     */
    data object MissingProfile : ContinueRegisterFragmentStatus()

    fun isLoading() = this is Loading

    fun isEmailVerified() = this !is EmailNotVerified

    fun isEmailNotVerified() = this is EmailNotVerified

}