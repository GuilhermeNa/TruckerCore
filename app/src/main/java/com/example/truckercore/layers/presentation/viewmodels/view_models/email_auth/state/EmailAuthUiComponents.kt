package com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.state

import com.example.truckercore.core.my_lib.expressions.isEmailFormat
import com.example.truckercore.core.my_lib.ui_components.TextInputComponent

data class EmailAuthUiComponents(
    val emailComponent: TextInputComponent = TextInputComponent(),
    val passwordComponent: TextInputComponent = TextInputComponent(),
    val confirmationComponent: TextInputComponent = TextInputComponent()
) {

    fun updateEmail(email: String): EmailAuthUiComponents {
        val updatedEmail = when {
            email.isEmpty() -> TextInputComponent(text = email, errorText = MSG_EMPTY_FIELD)
            !email.isEmailFormat() -> TextInputComponent(
                text = email,
                errorText = MSG_INVALID_EMAIL
            )

            else -> TextInputComponent(text = email, isValid = true)
        }
        return copy(emailComponent = updatedEmail)
    }

    fun updatePassword(password: String): EmailAuthUiComponents {
        val updatedPassword = when {
            password.isEmpty() -> TextInputComponent(text = password, errorText = MSG_EMPTY_FIELD)
            password.length !in 6..12 -> TextInputComponent(
                text = password,
                errorText = MSG_INVALID_PASS
            )

            else -> TextInputComponent(text = password, isValid = true)
        }
        return copy(passwordComponent = updatedPassword)
    }

    fun updateConfirmation(confirmation: String): EmailAuthUiComponents {
        val password = passwordComponent.text
        val updatedConfirmation = when {
            confirmation.isEmpty() -> TextInputComponent(
                text = confirmation,
                errorText = MSG_EMPTY_FIELD
            )

            confirmation != password -> TextInputComponent(
                text = confirmation,
                errorText = MSG_INVALID_CONFIRMATION
            )

            else -> TextInputComponent(text = confirmation, isValid = true)
        }
        return copy(confirmationComponent = updatedConfirmation)
    }

    companion object {
        private const val MSG_EMPTY_FIELD = "Este campo deve ser preenchido"

        private const val MSG_INVALID_EMAIL = "Formato de email inválido"

        private const val MSG_INVALID_PASS = "Senha deve ter entre 6 e 12 caracteres"

        private const val MSG_INVALID_CONFIRMATION = "Confirmação inválida"
    }

}
