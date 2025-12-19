package com.example.truckercore.layers.presentation.login.view_model.email_auth.helpers

import com.example.truckercore.core.my_lib.expressions.isEmailFormat
import com.example.truckercore.core.my_lib.ui_components.TextInputComponent

/**
 * Represents the UI components and validation logic for the [EmailAuthFragment] screen.
 *
 * Each component is represented by a [TextInputComponent], holding the current text,
 * validation state, and any error messages.
 */
data class EmailAuthUiComponents(
    val emailComponent: TextInputComponent = TextInputComponent(),
    val passwordComponent: TextInputComponent = TextInputComponent(),
    val confirmationComponent: TextInputComponent = TextInputComponent()
) {

    /**
     * Updates the email component with the provided [email] value.
     *
     * Validation rules:
     * - Field must not be empty
     * - Must follow a valid email format
     *
     * Returns a new instance of [EmailAuthUiComponents] with updated email state.
     */
    fun updateEmail(email: String): EmailAuthUiComponents {
        val updatedEmail = when {
            email.isEmpty() ->
                TextInputComponent(text = email, errorText = MSG_EMPTY_FIELD)

            !email.isEmailFormat() ->
                TextInputComponent(text = email, errorText = MSG_INVALID_EMAIL)

            else -> TextInputComponent(text = email, isValid = true)
        }
        return copy(emailComponent = updatedEmail)
    }

    /**
     * Updates the password component with the provided [password] value.
     *
     * Validation rules:
     * - Field must not be empty
     * - Password length must be between 6 and 12 characters
     *
     * Returns a new instance of [EmailAuthUiComponents] with updated password state.
     */
    fun updatePassword(password: String): EmailAuthUiComponents {
        val updatedPassword = when {
            password.isEmpty() ->
                TextInputComponent(text = password, errorText = MSG_EMPTY_FIELD)

            password.length !in 6..12 ->
                TextInputComponent(text = password, errorText = MSG_INVALID_PASS)

            else -> TextInputComponent(text = password, isValid = true)
        }
        return copy(passwordComponent = updatedPassword)
    }

    /**
     * Updates the confirmation component with the provided [confirmation] value.
     *
     * Validation rules:
     * - Field must not be empty
     * - Confirmation must match the current password
     *
     * Returns a new instance of [EmailAuthUiComponents] with updated confirmation state.
     */
    fun updateConfirmation(confirmation: String): EmailAuthUiComponents {
        val password = passwordComponent.text
        val updatedConfirmation = when {
            confirmation.isEmpty() ->
                TextInputComponent(text = confirmation, errorText = MSG_EMPTY_FIELD)

            confirmation != password ->
                TextInputComponent(text = confirmation, errorText = MSG_INVALID_CONFIRMATION)

            else -> TextInputComponent(text = confirmation, isValid = true)
        }
        return copy(confirmationComponent = updatedConfirmation)
    }

    /**
     * Sets an error on the email component indicating a user collision.
     *
     * Returns a new instance of [EmailAuthUiComponents] with updated email state.
     */
    fun warnUserCollision(): EmailAuthUiComponents {
        val newEmail = TextInputComponent(errorText = MSG_USER_COLLISION)
        return copy(emailComponent = newEmail)
    }

    companion object {
        private const val MSG_EMPTY_FIELD = "Este campo deve ser preenchido"
        private const val MSG_INVALID_EMAIL = "Formato de email inválido"
        private const val MSG_INVALID_PASS = "Senha deve ter entre 6 e 12 caracteres"
        private const val MSG_INVALID_CONFIRMATION = "Confirmação inválida"
        private const val MSG_USER_COLLISION = "Já existe um usuário com este email"
    }

}
