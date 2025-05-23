package com.example.truckercore.view_model.view_models.login

import com.example.truckercore._utils.classes.FieldState
import com.example.truckercore.model.shared.utils.expressions.isEmailFormat

object LoginValidator {

    fun getPasswordState(password: String): FieldState {
        if (password.isBlank()) return FieldState(text = password)

        if (password.length !in 6..12) {
            return FieldState(
                text = password,
                status = FieldState.Input.ERROR,
                message = "A senha deve conter entre 6 e 12 caracteres"
            )
        }

        return FieldState(
            text = password,
            status = FieldState.Input.VALID
        )
    }

    fun getEmailState(email: String): FieldState {
        if (email.isBlank()) return FieldState(text = email)

        if (!email.isEmailFormat()) {
            return FieldState(
                text = email,
                status = FieldState.Input.ERROR,
                message = "Formato de email inválido"
            )
        }

        return FieldState(
            text = email,
            status = FieldState.Input.VALID
        )
    }

    fun getButtonState(
        passwordValidation: FieldState.Input,
        emailValidation: FieldState.Input
    ): Boolean = passwordValidation == FieldState.Input.VALID
            && emailValidation == FieldState.Input.VALID


}