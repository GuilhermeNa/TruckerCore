package com.example.truckercore.layers.presentation.login.view_model.login.helpers

import com.example.truckercore.core.my_lib.expressions.isEmailFormat
import com.example.truckercore.core.my_lib.expressions.isPasswordFormat
import com.example.truckercore.layers.presentation.base.contracts.State
import com.example.truckercore.layers.presentation.login.view.fragments.login.LoginFragment

/**
 * Represents the UI state for the [LoginFragment].
 *
 * This state tracks:
 * - Validation messages for the email and password fields.
 * - The current login status (waiting for input, ready to submit, or attempting login).
 *
 * The class is immutable; every state-changing operation returns a new instance,
 * ensuring predictable state flows for MVI / MVVM architectures.
 *
 * @param emailMsg Validation message for the email field, or `null` if valid.
 * @param passMsg Validation message for the password field, or `null` if valid.
 * @param status Current state of the login process.
 */
data class LoginFragmentState(
    val emailMsg: String? = null,
    val passMsg: String? = null,
    val status: LoginFragmentStatus = LoginFragmentStatus.WaitingInput
) : State {

    /**
     * Determines whether both fields are valid and the login action can proceed.
     */
    private val isReadyToLogin get() = emailMsg == null && passMsg == null

    /**
     * Updates the email field and recalculates the validation message and status.
     *
     * @param txt The new email input provided by the user.
     * @return A new [LoginFragmentState] containing the updated email validation result.
     */
    fun updateEmail(txt: String): LoginFragmentState {
        val newMsg = defineEmailStateMsg(txt)
        val newStatus = defineStatus(newEmailMsg = newMsg)
        return copy(emailMsg = newMsg, status = newStatus)
    }

    /**
     * Updates the password field and recalculates the validation message and status.
     *
     * @param txt The new password input provided by the user.
     * @return A new [LoginFragmentState] containing the updated password validation result.
     */
    fun updatePassword(txt: String): LoginFragmentState {
        val newMsg = definePassStateMsg(txt)
        val newStatus = defineStatus(newPassMsg = newMsg)
        return copy(passMsg = newMsg, status = newStatus)
    }

    /**
     * Called when the user attempts to log in.
     *
     * @throws IllegalStateException if any validation errors are present.
     * @return A new state indicating that login is being attempted.
     */
    fun tryToLogin(): LoginFragmentState {
        if (!isReadyToLogin) throw IllegalStateException(ILLEGAL_STATE_MSG)
        return copy(status = LoginFragmentStatus.TryingLogin)
    }

    /**
     * Sets error messages when the backend reports invalid credentials.
     *
     * @return A state with updated error messages and reset status.
     */
    fun invalidCredentials() = copy(
        emailMsg = EMAIL_NOT_FOUND_ERROR_MSG,
        passMsg = PASSWORD_INVALID_MSG,
        status = LoginFragmentStatus.WaitingInput
    )

    //----------------------------------------------------------------------------------------------
    // VALIDATION HELPERS
    //----------------------------------------------------------------------------------------------
    /**
     * Validates the email input and returns an appropriate error message,
     * or `null` if the email is valid.
     */
    private fun defineEmailStateMsg(input: String) = when {
        input.isBlank() -> EMAIL_BLANK_ERROR_MSG
        !input.isEmailFormat() -> EMAIL_WRONG_FORMAT_MSG
        else -> null
    }

    /**
     * Validates the password input and returns an appropriate error message,
     * or `null` if the password is valid.
     */
    private fun definePassStateMsg(input: String) = when {
        input.isBlank() -> PASSWORD_BLANK_ERROR_MSG
        !input.isPasswordFormat() -> PASSWORD_WRONG_FORMAT_MSG
        else -> null
    }

    /**
     * Computes the new high-level state of the fragment based on the latest
     * validation results of the email and password fields.
     *
     * @param newEmailMsg Optional fresh validation result for the email field.
     * @param newPassMsg Optional fresh validation result for the password field.
     */
    private fun defineStatus(
        newEmailMsg: String? = null,
        newPassMsg: String? = null
    ) = if ((newEmailMsg ?: emailMsg) == null && (newPassMsg ?: passMsg) == null) {
        LoginFragmentStatus.ReadyToLogin
    } else {
        LoginFragmentStatus.WaitingInput
    }

    //----------------------------------------------------------------------------------------------
    // CONSTANTS
    //----------------------------------------------------------------------------------------------
    private companion object {
        private const val EMAIL_BLANK_ERROR_MSG = "Por favor, informe seu e-mail."
        private const val EMAIL_WRONG_FORMAT_MSG = "Formato de e-mail inválido."
        private const val EMAIL_NOT_FOUND_ERROR_MSG = "E-mail não encontrado."

        private const val PASSWORD_BLANK_ERROR_MSG = "Por favor, informe sua senha."
        private const val PASSWORD_WRONG_FORMAT_MSG =
            "A senha deve ser numérica e ter entre 6 e 12 dígitos."
        private const val PASSWORD_INVALID_MSG = "Senha incorreta."

        private const val ILLEGAL_STATE_MSG = "Não é possível continuar: dados inválidos."
    }

}