package com.example.truckercore.layers.presentation.login.view_model.user_profile.helpers

/**
 * Represents the different UI states associated with the user profile creation flow.
 * This status determines how the fragment should behave at any given moment, such as:
 * - Whether the user is still typing,
 * - Whether the input is valid and ready to submit,
 * - Whether the profile creation task is currently in progress.
 */
sealed class UserProfileFragmentStatus {

    /**
     * Indicates that the user is still entering or adjusting the input,
     * and the data is not yet ready for submission.
     */
    data object WaitingInput : UserProfileFragmentStatus()

    /**
     * Indicates that the input has been validated and the user may proceed
     * with profile creation.
     */
    data object ReadyToCreate : UserProfileFragmentStatus()

    /**
     * Represents the state in which the profile creation task is currently
     * being executed, typically showing a loading UI.
     */
    data object Creating : UserProfileFragmentStatus()

    /**
     * @return `true` if the status indicates that a creation operation is in progress.
     */
    fun isCreating(): Boolean = this is Creating

    /**
     * @return `true` if the input has been validated and is ready for submission.
     */
    fun isReadyToCreate(): Boolean = this is ReadyToCreate

}