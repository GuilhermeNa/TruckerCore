package com.example.truckercore.view_model.view_models.user_name

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore.model.shared.utils.expressions.isNameFormat
import com.example.truckercore.view_model.view_models.user_name.UserNameFragState.UserNameFragErrorType
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel that manages the business logic and state for the UserNameFragment.
 * It validates the user input, provides the appropriate state for the fragment, and
 * handles the interaction with the fragment's UI.
 */
class UserNameViewModel : ViewModel() {

    // State Flow for holding and exposing the fragment's state
    private val _fragState: MutableStateFlow<UserNameFragState> =
        MutableStateFlow(UserNameFragState.WaitingForInput)
    val fragState get() = _fragState.asStateFlow()

    // Shared Flow for holding and exposing the fragment's events
    private val _fragEvent = MutableSharedFlow<UserNameFragEvent>()
    val fragEvent get() = _fragEvent.asSharedFlow()  // Exposed immutable SharedFlow

    // Helper classes for validating input and managing state transitions
    private val validateEntries = ValidateEntries()
    private val stateProvider = StateProvider()

    //----------------------------------------------------------------------------------------------

    /**
     * Sets a new event for the fragment.
     * This is used to communicate between the ViewModel and the Fragment for triggering actions.
     */
    fun setEvent(newEvent: UserNameFragEvent) {
        viewModelScope.launch {
            _fragEvent.emit(newEvent)
        }
    }

    /**
     * Sets the new state for the fragment, updating the fragment's UI based on the state.
     */
    fun setState(newState: UserNameFragState) {
        _fragState.value = newState
    }

    /**
     * Validates the user input (username) and updates the state accordingly.
     * It checks for empty names, name size, and whether the name contains at least two words.
     */
    fun validateName(name: String) {
        val validationResult = validateEntries(name)
        val newState = stateProvider(name, validationResult)
        setState(newState)
    }

    //----------------------------------------------------------------------------------------------
    // Helper classes
    //----------------------------------------------------------------------------------------------

    /**
     * Class responsible for validating the user input (name).
     */
    private class ValidateEntries {

        /**
         * Validates the user input for different error types, including empty name, size constraints,
         * incomplete name, and invalid name format.
         */
        operator fun invoke(name: String): UserNameFragErrorType? {
            val trimmedName = name.trimMargin()
            val wordArr = name.split(" ")

            return when {
                trimmedName.isEmpty() -> UserNameFragErrorType.NameIsEmpty
                !trimmedName.sizeIsValid() -> UserNameFragErrorType.InvalidSize
                wordArr.size == 1 -> UserNameFragErrorType.CompleteNameRequired
                trimmedName.isAnyWordInWrongFormat() -> UserNameFragErrorType.InvalidName
                else -> null
            }
        }

        private fun String.sizeIsValid(): Boolean = length in 5..29

        private fun String.isAnyWordInWrongFormat(): Boolean {
            val wordArr = this.split(" ")
            return wordArr.any { !it.isNameFormat() }
        }

    }

    /**
     * Class responsible for generating the appropriate state based on validation results.
     */
    private class StateProvider {

        /**
         * Returns the new state based on the validation result.
         * If there is an error, it returns an error state, otherwise a valid name state.
         */
        operator fun invoke(name: String, error: UserNameFragErrorType?): UserNameFragState =
            if (error == null) UserNameFragState.ValidName(name)
            else UserNameFragState.Error(error)

    }

}

