package com.example.truckercore.view_model.view_models.login

import com.example.truckercore._utils.classes.FieldState
import com.example.truckercore._utils.classes.Input
import com.example.truckercore.model.shared.utils.expressions.isEmailFormat

object LoginValidator {

    fun getPasswordState(password: String): FieldState {
        if (password.isBlank()) return FieldState(text = password)

        if (password.length !in 6..12) {
            return FieldState(
                text = password,
                validation = Input.ERROR,
                stateMessage = "A senha deve conter entre 6 e 12 caracteres"
            )
        }

        return FieldState(
            text = password,
            validation = Input.VALID
        )
    }

    fun getEmailState(email: String): FieldState {
        if (email.isBlank()) return FieldState(text = email)

        if (!email.isEmailFormat()) {
            return FieldState(
                text = email,
                validation = Input.ERROR,
                stateMessage = "Formato de email inválido"
            )
        }

        return FieldState(
            text = email,
            validation = Input.VALID
        )
    }

    fun getButtonState(
        passwordValidation: Input,
        emailValidation: Input
    ): Boolean = passwordValidation == Input.VALID
            && emailValidation == Input.VALID


}