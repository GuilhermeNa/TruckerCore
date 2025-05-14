package com.example.truckercore.view_model.view_models.user_name

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore.model.infrastructure.integration.preferences.PreferencesRepository
import com.example.truckercore.model.modules.authentication.manager.AuthManager
import com.example.truckercore.model.shared.utils.expressions.isNameFormat
import com.example.truckercore._utils.classes.FullName
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel that manages the business logic and state for the UserNameFragment.
 * This ViewModel handles the user input validation, updates the state of the fragment,
 * and manages the interaction between the fragment's UI and the business logic.
 *
 * It provides the fragment with the following:
 * - The current state of the fragment.
 * - The events triggered by the fragment.
 * - The effects that can be emitted to update the UI or navigate.
 *
 * @param authService An instance of AuthService to interact with the authentication service.
 */
class UserNameViewModel(
    private val preferences: PreferencesRepository,
    private val authService: AuthManager
) : ViewModel() {

    // State Flow for holding and exposing the current state of the fragment
    private val _fragState: MutableStateFlow<UserNameFragState> =
        MutableStateFlow(UserNameFragState.Initial)
    val state get() = _fragState.asStateFlow()

    // Shared Flow for holding and exposing fragment events like button clicks, etc.
    private val _fragEvent = MutableSharedFlow<UserNameFragEvent>()
    val event get() = _fragEvent.asSharedFlow()  // Exposed immutable SharedFlow

    // Shared Flow for handling UI effects such as navigation or UI updates
    private val _effect = MutableSharedFlow<UserNameFragEffect>()
    val effect get() = _effect.asSharedFlow()

    // Instance of InputValidator to validate the user input for the profile update
    private val inputValidator = InputValidator()

    //----------------------------------------------------------------------------------------------

    /**
     * Attempts to validate the user input (username), and updates the state accordingly.
     * If the input is valid, it proceeds to update the user's profile. If the input is invalid,
     * it sets an error state for the fragment.
     *
     * @param name The input username from the user.
     */
    fun tryUpdateProfile(name: String) {
        viewModelScope.launch {
            setState(UserNameFragState.Updating)

            delay(500) // Optional UI delay for user experience

            // Validate the input username
            val validationResult = inputValidator.invoke(name)
            if (validationResult != null) {
                // If the input is invalid, update the state with an error message
                setState(UserNameFragState.UserInputError(validationResult))
                return@launch
            }

            delay(500) // Optional UI delay for user experience

            // Create a UserProfile object for updating

            // Attempt to update the username and set the appropriate effect based on the result
         /*   val newEffect = result.mapAppResult(
                onSuccess = {
                    markNameStepComplete()
                    setState(UserNameFragState.Initial)
                    UserNameFragEffect.ProfileUpdated
                },
                onError = { e ->
                    UserNameFragEffect.ProfileUpdateFailed(e)
                }
            )
            setEffect(newEffect)*/
        }
    }

/*
    */
/**
     * Calls the authentication service to update the user's profile with the provided user profile.
     *
     * @param profile The user profile containing the new full name to be updated.
     * @return The result of the update operation.
     *//*

    private suspend fun updateUserName(profile: UserCategory) = authService.updateUserName(profile)
*/

    /**
     * Sets a new event for the fragment to trigger UI actions such as clicks.
     * This communicates between the ViewModel and the Fragment to trigger specific actions.
     *
     * @param newEvent The event that needs to be emitted (e.g., FAB click).
     */
    fun setEvent(newEvent: UserNameFragEvent) {
        viewModelScope.launch {
            _fragEvent.emit(newEvent)
        }
    }

    /**
     * Updates the state of the fragment. The UI will be updated based on the new state.
     * This function is called to update the fragment's state in response to different events.
     *
     * @param newState The new state to be set for the fragment.
     */
    private fun setState(newState: UserNameFragState) {
        _fragState.value = newState
    }

    /**
     * Emits an effect to update the UI, trigger navigation, or show feedback based on the result.
     *
     * @param newEffect The effect to be emitted (e.g., profile update success).
     */
    private fun setEffect(newEffect: UserNameFragEffect) {
        viewModelScope.launch {
            _effect.emit(newEffect)
        }
    }

    private fun markNameStepComplete() {
        viewModelScope.launch {
          /*  preferences.markStepAsCompleted(RegistrationStep.Name)*/
        }
    }

}

/**
 * A helper class to validate the user input for the username field.
 * The validation rules include:
 * - The username must not be empty.
 * - The username must contain at least two words.
 * - The length must be between 5 and 29 characters.
 * - Each word must contain only valid characters (letters only).
 */
private class InputValidator {

    /**
     * Validates the input username string against predefined rules.
     *
     * - If the name is empty, returns an error message for empty input.
     * - If the name consists of only one word, returns an error message requesting a full name.
     * - If the length of the name is not within the acceptable range (5-29 characters), returns an error.
     * - If any word in the name contains invalid characters (anything other than letters), returns an error.
     *
     * @param name The user input string to be validated.
     * @return A validation error message or null if the input is valid.
     */
    operator fun invoke(name: String): String? {
        val wordArr = name.split(" ")

        return when {
            name.isEmpty() -> NAME_EMPTY
            wordArr.size == 1 -> COMPLETE_NAME_REQUIRED
            !name.sizeIsValid() -> INVALID_SIZE
            name.isAnyWordInWrongFormat() -> INVALID_NAME
            else -> null
        }
    }

    /**
     * Checks if the length of the input string is valid (between 5 and 29 characters).
     */
    private fun String.sizeIsValid(): Boolean = length in 5..29

    /**
     * Checks if any word in the input string contains invalid characters.
     * A valid word should only contain letters (A-Z, a-z).
     */
    private fun String.isAnyWordInWrongFormat(): Boolean {
        val wordArr = this.split(" ")
        return wordArr.any { !it.isNameFormat() }
    }

    companion object {
        private const val NAME_EMPTY = "Field is required."
        private const val INVALID_NAME = "Invalid format, only letters are allowed."
        private const val INVALID_SIZE = "Name must be between 5 and 30 characters."
        private const val COMPLETE_NAME_REQUIRED = "Please enter your full name."
    }

}
