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
    data object WaitingForInput : UserNameFragState()

    /**
     * State when the user has entered a valid name.
     * The valid name is passed as a parameter.
     *
     * @param name The valid name entered by the user.
     */
    data class ValidName(val name: String) : UserNameFragState()

    /**
     * State when there is an error in the user input (e.g., invalid format, size, or empty name).
     * The error type is passed as a parameter.
     *
     * @param type The type of error that occurred during validation.
     */
    data class Error(val type: UserNameFragErrorType) : UserNameFragState()

    //----------------------------------------------------------------------------------------------

    /**
     * Enum class to define different types of validation errors that may occur in the fragment.
     * Each error type holds a message that can be displayed to the user.
     */
    enum class UserNameFragErrorType(val message: String) {
        /**
         * Error when the name input is empty.
         */
        NameIsEmpty("Campo obrigatório."),

        /**
         * Error when the name contains invalid characters (i.e., it should only contain letters).
         */
        InvalidName("Formato inválido, apenas letras são aceitas."),

        /**
         * Error when the name does not meet the required length (must be between 4 and 30 characters).
         */
        InvalidSize("Nome deve ter entre 4 e 30 caracteres."),

        /**
         * Error when the user only enters a single name instead of a complete name (first and last name).
         */
        CompleteNameRequired("Por favor digite seu nome completo.");
    }

}


