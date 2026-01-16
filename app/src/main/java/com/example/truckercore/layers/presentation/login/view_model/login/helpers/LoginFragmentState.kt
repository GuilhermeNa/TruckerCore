package com.example.truckercore.layers.presentation.login.view_model.login.helpers

import com.example.truckercore.core.my_lib.expressions.isEmailFormat
import com.example.truckercore.core.my_lib.expressions.isPasswordFormat
import com.example.truckercore.core.my_lib.ui_components.TextInputComponent
import com.example.truckercore.layers.presentation.base.contracts.State
import com.example.truckercore.layers.presentation.login.view.fragments.login.LoginFragment

/**
 * Represents the immutable UI state for the [LoginFragment].
 *
 * This state holds the current values and validation results for the
 * email and password input components, as well as the overall screen status.
 *
 * This class follows immutability principles: every state change results
 * in a new instance via [copy], making it predictable and easy to reason about.
 */
data class LoginFragmentState(
    val emailComponent: TextInputComponent = TextInputComponent(),
    val passwordComponent: TextInputComponent = TextInputComponent(),
    val status: LoginFragmentStatus = LoginFragmentStatus.WaitingInput
) : State {

    /**
     * Updates the state to indicate that a login attempt is in progress.
     */
    fun tryToLogin() = copy(status = LoginFragmentStatus.TryingLogin)

    /**
     * Resets the state after an invalid credential attempt, returning
     * the screen to a state where user input is expected.
     */
    fun invalidCredentials() = copy(status = LoginFragmentStatus.WaitingInput)

    /**
     * Updates the state to indicate that the screen is ready to perform
     * the login operation.
     */
    fun readyToCreate() = copy(status = LoginFragmentStatus.ReadyToLogin)

    /**
     * Updates the email input component and recalculates the overall screen status.
     *
     * @param email The new email value provided by the user
     * @return A new [LoginFragmentState] with the updated email component
     */
    fun updateEmail(email: String): LoginFragmentState {
        val updatedEmail = createEmailComponent(email)
        val updatedStatus = determineStatus(emailComp = updatedEmail)
        return copy(emailComponent = updatedEmail, status = updatedStatus)
    }

    /**
     * Creates and validates a [TextInputComponent] for the email field.
     *
     * Validation rules:
     * - Email must not be blank
     * - Email must follow a valid email format
     */
    private fun createEmailComponent(email: String) = when {
        email.isBlank() ->
            TextInputComponent(text = email, isValid = false)

        !email.isEmailFormat() ->
            TextInputComponent(text = email, errorText = EMAIL_WRONG_FORMAT_MSG)

        else ->
            TextInputComponent(text = email, isValid = true)
    }

    /**
     * Updates the password input component and recalculates the overall screen status.
     *
     * @param password The new password value provided by the user
     * @return A new [LoginFragmentState] with the updated password component
     */
    fun updatePassword(password: String): LoginFragmentState {
        val updatedPassword = createPasswordComponent(password)
        val updatedStatus = determineStatus(passwordComp = updatedPassword)
        return copy(passwordComponent = updatedPassword, status = updatedStatus)
    }

    /**
     * Creates and validates a [TextInputComponent] for the password field.
     *
     * Validation rules:
     * - Password must not be blank
     * - Password must be numeric
     * - Password length must be between 6 and 12 digits
     */
    private fun createPasswordComponent(password: String) = when {
        password.isBlank() ->
            TextInputComponent(text = password, isValid = false)

        !password.isPasswordFormat() ->
            TextInputComponent(text = password, errorText = PASSWORD_WRONG_FORMAT_MSG)

        else ->
            TextInputComponent(text = password, isValid = true)
    }

    /**
     * Determines the overall screen status based on the validity of
     * the email and password input components.
     *
     * @param emailComp Optional email component override
     * @param passwordComp Optional password component override
     * @return The calculated [LoginFragmentStatus]
     */
    private fun determineStatus(
        emailComp: TextInputComponent = emailComponent,
        passwordComp: TextInputComponent = passwordComponent
    ) = when {
        emailComp.isValid && passwordComp.isValid -> LoginFragmentStatus.ReadyToLogin
        else -> LoginFragmentStatus.WaitingInput
    }

    //----------------------------------------------------------------------------------------------
    // CONSTANTS
    //----------------------------------------------------------------------------------------------
    private companion object {

        private const val EMAIL_WRONG_FORMAT_MSG =
            "Formato de e-mail inválido."

        private const val PASSWORD_WRONG_FORMAT_MSG =
            "A senha deve ser numérica e ter entre 6 e 12 dígitos."

    }

}