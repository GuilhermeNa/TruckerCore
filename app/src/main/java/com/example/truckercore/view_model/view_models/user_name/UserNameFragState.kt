package com.example.truckercore.view_model.view_models.user_name

import com.example.truckercore.view.fragments.user_name.UserNameFragment

/**
 * Sealed class to represent the different states of the [UserNameFragment].
 * Collected and handled by the [UserNameViewModel] to manage fragment interactions.
 */
sealed class UserNameFragState {

    /**
     * State when the fragment is waiting for user input.
     * This state is typically the initial state when the fragment is ready for interaction.
     */
    data object Initial : UserNameFragState()

    /**
     * State when the fragment is trying to update the user profile.
     */
    data object Updating : UserNameFragState()

    /**
     * State when there is an error in the user input (e.g., invalid format, size, or empty name).
     * The error type is passed as a parameter.
     *
     * @param text The error text of what occurred during validation.
     */
    data class UserInputError(val text: String) : UserNameFragState()

}


