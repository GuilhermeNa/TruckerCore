package com.example.truckercore.view_model.view_models.email_auth.uiState

import com.example.truckercore.model.shared.utils.expressions.isEmailFormat
import com.example.truckercore.view_model._shared.components.ButtonComponent
import com.example.truckercore.view_model._shared.components.TextInputComponent

class EmailAuthComponentFactory {

    fun email(email: String): TextInputComponent {
        return when {
            email.isEmpty() -> TextInputComponent(text = email, errorText = MSG_EMPTY_FIELD)
            !email.isEmailFormat() -> TextInputComponent(text = email, errorText = MSG_INVALID_EMAIL)
            else -> TextInputComponent(text = email, isValid = true)
        }
    }

    fun password(password: String): TextInputComponent {
        return when {
            password.isEmpty() -> TextInputComponent(text = password, errorText = MSG_EMPTY_FIELD)
            password.length !in 6..12 -> TextInputComponent(text = password, errorText = MSG_INVALID_PASS)
            else -> TextInputComponent(text = password, isValid = true)
        }
    }

    fun confirmation(confirmation: String, password: String): TextInputComponent {
        return when {
            confirmation.isEmpty() -> TextInputComponent(text = confirmation, errorText = MSG_EMPTY_FIELD)
            confirmation != password -> TextInputComponent(text = confirmation, errorText = MSG_INVALID_CONFIRMATION)
            else -> TextInputComponent(text = confirmation, isValid = true)
        }
    }

    fun button(
        emailComponent: TextInputComponent,
        passwordComponent: TextInputComponent,
        confirmationComponent: TextInputComponent
    ): ButtonComponent {
        return if (emailComponent.isValid && passwordComponent.isValid && confirmationComponent.isValid) {
            ButtonComponent(isEnabled = true)
        } else ButtonComponent(isEnabled = false)
    }

    companion object {
        private const val MSG_EMPTY_FIELD = "Este campo deve ser preenchido"

        private const val MSG_INVALID_EMAIL = "Formato de email inválido"

        private const val MSG_INVALID_PASS = "Senha deve ter entre 6 e 12 caracteres"

        private const val MSG_INVALID_CONFIRMATION = "Confirmação inválida"
    }

}