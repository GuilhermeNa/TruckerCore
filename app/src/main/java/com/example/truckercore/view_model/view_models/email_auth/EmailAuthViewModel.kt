package com.example.truckercore.view_model.view_models.email_auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore.model.infrastructure.integration.auth.for_app.requirements.EmailCredential
import com.example.truckercore.model.modules.authentication.service.AuthService
import com.example.truckercore.model.shared.utils.expressions.isEmailFormat
import com.example.truckercore.model.shared.utils.expressions.mapAppResult
import com.example.truckercore.model.shared.value_classes.Email
import com.example.truckercore.model.shared.value_classes.Password
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.UserInputError
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * EmailAuthViewModel is responsible for managing the state, events, and effects related to the EmailAuthFragment.
 * It handles validation, user creation logic, and emits appropriate UI signals for the fragment to react to.
 *
 * @param authService A service responsible for interacting with the authentication backend.
 */
class EmailAuthViewModel(private val authService: AuthService) : ViewModel() {

    // StateFlow that holds the current UI state of the fragment
    private val _state: MutableStateFlow<EmailAuthFragState> =
        MutableStateFlow(EmailAuthFragState.WaitingInput)
    val state get() = _state.asStateFlow()

    // SharedFlow for emitting one-time events like button clicks
    private val _event: MutableSharedFlow<EmailAuthFragEvent> = MutableSharedFlow()
    val event get() = _event.asSharedFlow()

    // SharedFlow for emitting one-time effects such as success or failure feedback
    private val _effect: MutableSharedFlow<EmailAuthFragEffect> = MutableSharedFlow()
    val effect get() = _effect.asSharedFlow()

    // Helper class responsible for validate user input text
    private val inputValidator = InputValidator()

    /**
     * Attempts to authenticate the user by first validating input and then creating a new account.
     *
     * If validation fails, an Error state is emitted. Otherwise, authentication proceeds.
     *
     * @param email User input email
     * @param password User input password
     * @param confirmation Password confirmation
     */
    fun tryToAuthenticate(email: String, password: String, confirmation: String) {
        viewModelScope.launch {
            delay(500) // Optional UI delay

            val validationResult = inputValidator(email, password, confirmation)
            if (validationResult.hasErrors) {
                setState(UserInputError(validationResult))
                return@launch
            }

            delay(500) // Optional UI delay

            val credential = EmailCredential(Email(email), Password.from(password))
            val result = authenticateUserWithEmail(credential)
            val newEffect = result.mapAppResult(
                onSuccess = { EmailAuthFragEffect.UserCreated },
                onError = { e -> EmailAuthFragEffect.UserCreationFailed(e) }
            )

            setEffect(newEffect)
        }
    }

    /**
     * Uses the AuthService to create a user with the provided credentials,
     * and emits a success or failure effect accordingly.
     */
    private suspend fun authenticateUserWithEmail(credential: EmailCredential) =
        authService.createUserWithEmail(credential)

    /**
     * Emits a new UI event to be consumed by the fragment.
     */
    fun setEvent(newEvent: EmailAuthFragEvent) {
        viewModelScope.launch {
            _event.emit(newEvent)
        }
    }

    /**
     * Emits a UI effect to trigger a side effect in the fragment (e.g., navigation or dialog).
     */
    private fun setEffect(newEffect: EmailAuthFragEffect) {
        viewModelScope.launch {
            _effect.emit(newEffect)
        }
    }

    /**
     * Updates the current UI state.
     */
    fun setState(newState: EmailAuthFragState) {
        _state.value = newState
    }

}

/**
 * Validates email, password, and confirmation inputs.
 *
 * @return An [EmailAuthUserInputValidationResult] of user-readable error messages.
 */
private class InputValidator {

    operator fun invoke(
        email: String,
        password: String,
        confirmation: String
    ): EmailAuthUserInputValidationResult {
        var inputResult = EmailAuthUserInputValidationResult()

        if (email.isEmpty()) {
            inputResult = inputResult.addEmailError(EMPTY_EMAIL_ERROR)
        } else if (!email.isEmailFormat()) {
            inputResult = inputResult.addEmailError(EMAIL_WRONG_FORMAT)
        }

        if (password.isEmpty()) {
            inputResult = inputResult.addPassError(EMPTY_PASSWORD_ERROR)
        } else if (password.length !in 6..12) {
            inputResult = inputResult.addPassError(PASSWORD_WRONG_FORMAT)
        }

        if (confirmation.isEmpty()) {
            inputResult = inputResult.addConfirmationError(EMPTY_CONFIRMATION_ERROR)
        } else if (confirmation != password) {
            inputResult = inputResult.addConfirmationError(INCOMPATIBLE_CONFIRMATION_ERROR)
        }

        return inputResult
    }

    companion object {
        private const val EMPTY_EMAIL_ERROR = "E-mail cannot be empty."
        private const val EMAIL_WRONG_FORMAT = "Invalid email format."
        private const val EMPTY_PASSWORD_ERROR = "Password cannot be empty."
        private const val PASSWORD_WRONG_FORMAT = "Password must be between 6 and 12 characters."
        private const val EMPTY_CONFIRMATION_ERROR = "Confirmation cannot be empty."
        private const val INCOMPATIBLE_CONFIRMATION_ERROR = "Passwords do not match."
    }

}