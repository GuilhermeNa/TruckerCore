package com.example.truckercore.view.fragments.email_auth

import com.example.truckercore._utils.classes.Email
import com.example.truckercore._utils.classes.FieldState
import com.example.truckercore._utils.classes.Input
import com.example.truckercore._utils.classes.Password
import com.example.truckercore.model.infrastructure.integration.auth.for_app.data.EmailCredential
import com.example.truckercore.model.shared.utils.expressions.isEmailFormat

data class EmailAuthForm(
    val email: String,
    val password: String,
    val confirmation: String
) {

    fun validate(): EmailAuthValidationResult {
        val emailFieldState = when {
            email.isEmpty() -> "Preencha o campo email"
            !email.isEmailFormat() -> "Formato de email inválido"
            else -> null
        }?.let { FieldState(validation = Input.ERROR, stateMessage = it) }
            ?: FieldState(validation = Input.VALID)

        val passwordFieldState = when {
            password.isEmpty() -> "Preencha o campo senha"
            password.length !in 6..12 -> "Senha deve ter entre 6 e 12 caracteres"
            else -> null
        }?.let { FieldState(validation = Input.ERROR, stateMessage = it) }
            ?: FieldState(validation = Input.VALID)

        val confirmationFieldState = when {
            confirmation.isEmpty() -> "Preencha o campo confirmação"
            confirmation != password -> "Senha e confirmação diferentes"
            else -> null
        }?.let { FieldState(validation = Input.ERROR, stateMessage = it) }
            ?: FieldState(validation = Input.VALID)

        return EmailAuthValidationResult(
            emailState = emailFieldState,
            passwordState = passwordFieldState,
            confirmationState = confirmationFieldState
        )
    }

    fun getCredential() = EmailCredential(Email.from(email), Password.from(password))

}

data class EmailAuthValidationResult(
    val emailState: FieldState,
    val passwordState: FieldState,
    val confirmationState: FieldState
) {

    fun hasFieldError() =
        emailState.validation == Input.ERROR ||
                passwordState.validation == Input.ERROR ||
                confirmationState.validation == Input.ERROR

}