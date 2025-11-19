package com.example.truckercore.layers.presentation.nav_login.view_model.user_name.state

import com.example.truckercore.core.my_lib.classes.Name
import com.example.truckercore.core.my_lib.expressions.isFullNameFormat
import com.example.truckercore.core.my_lib.ui_components.TextInputComponent
import com.example.truckercore.layers.presentation.base.contracts.State

/**
 * Represents the complete UI state for the user profile creation screen.
 *
 * This state contains:
 * - A [TextInputComponent] representing the user's name input field, including validation status.
 * - A [UserProfileFragmentStatus] describing whether the screen is waiting for input,
 *   ready to create a profile, or currently creating it.
 *
 * Utility functions are provided to update the name, transition state, and extract
 * validated domain-level information such as [Name].
 */
data class UserProfileFragmentState(
    val nameComponent: TextInputComponent,
    val status: UserProfileFragmentStatus
) : State {

    /**
     * Returns a new state indicating that the user is ready to create a profile.
     */
    fun readyToCreate() = copy(status = UserProfileFragmentStatus.ReadyToCreate)

    /**
     * Returns a new state indicating that the profile creation process is in progress.
     */
    fun creating() = copy(status = UserProfileFragmentStatus.Creating)

    /**
     * Updates the name component with the provided [name] string and determines the new status.
     *
     * Validation rules:
     * - Empty text → Shows an "empty field" error.
     * - Not in full name format → Shows an "invalid name" error.
     * - Valid input → Marks the component as valid.
     *
     * @param name The new input text for the name field.
     * @return A new state instance with updated validation and component status.
     */
    fun updateName(name: String): UserProfileFragmentState {
        val newNameComponent = when {
            name.isEmpty() ->
                TextInputComponent(text = name, errorText = MSG_EMPTY_FIELD)

            !name.isFullNameFormat() ->
                TextInputComponent(text = name, errorText = MSG_INVALID_NAME)

            else ->
                TextInputComponent(text = name, isValid = true)
        }

        return copy(
            nameComponent = newNameComponent,
            status = determineStatus(newNameComponent)
        )
    }

    /**
     * Determines the appropriate status based on whether the name component is valid.
     */
    private fun determineStatus(component: TextInputComponent) =
        if (component.isValid)
            UserProfileFragmentStatus.ReadyToCreate
        else
            UserProfileFragmentStatus.WaitingInput

    /**
     * Converts the current text into a domain-level [Name] instance.
     *
     * @return A validated [Name] object created from the name component text.
     */
    fun getName(): Name {
        val nameString = nameComponent.text
        return Name.from(nameString)
    }

    /**
     * @return true if the current state represents an ongoing profile creation operation.
     */
    fun isCreating() = status.isCreating()

    /**
     * @return true if the user input is valid and ready to be submitted.
     */
    fun isReadyToCreate() = status.isReadyToCreate()

    private companion object {
        private const val MSG_EMPTY_FIELD = "Este campo deve ser preenchido"
        private const val MSG_INVALID_NAME = "Preencha nome e sobrenome"
    }

}

