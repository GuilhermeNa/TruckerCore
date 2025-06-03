package com.example.truckercore.view_model.view_models.email_auth.uiState

import com.example.truckercore._shared.classes.ButtonState
import com.example.truckercore._shared.classes.FieldState
import com.example.truckercore.view_model._shared._contracts.State
import com.example.truckercore.model.shared.utils.expressions.isEmailFormat
import com.example.truckercore.view._shared.ui_error.UiError

data class EmailAuthUiState(
    val emailField: FieldState = FieldState(),
    val passwordField: FieldState = FieldState(),
    val confirmationField: FieldState = FieldState(),
    val createButton: ButtonState = ButtonState(false),
    val status: Status = Status.AwaitingInput.Default
) : State {

    fun updateEmail(email: String): EmailAuthUiState {
        val (status, message) = when {
            email.isEmpty() -> Pair(FieldState.Input.ERROR, "Preencha o campo email")
            !email.isEmailFormat() -> Pair(FieldState.Input.ERROR, "Formato de email inválido")
            else -> Pair(FieldState.Input.VALID, null)
        }

        val updatedField = FieldState(text = email, status = status, message = message)

        val inputStatus = when {
            updatedField.status == FieldState.Input.VALID &&
                    passwordField.status == FieldState.Input.VALID &&
                    confirmationField.status == FieldState.Input.VALID -> Status.AwaitingInput.Ready

            else -> {
                val errCode = listOf(
                    if (updatedField.status == FieldState.Input.ERROR) 1 else 0,
                    if (passwordField.status == FieldState.Input.ERROR) 1 else 0,
                    if (confirmationField.status == FieldState.Input.ERROR) 1 else 0
                ).joinToString(separator = "")
                inputErrorMap[errCode] ?: Status.AwaitingInput.Default
            }
        }

        val buttonStatus = when (inputStatus) {
            Status.AwaitingInput.Ready -> ButtonState(true)
            else -> ButtonState(false)
        }

        return this.copy(
            emailField = updatedField,
            status = inputStatus,
            createButton = buttonStatus
        )
    }

    fun updatePassword(password: String): EmailAuthUiState {
        val (status, message) = when {
            password.isEmpty() -> FieldState.Input.ERROR to "Preencha o campo senha"
            password.length !in 6..12 -> FieldState.Input.ERROR to "Senha deve ter entre 6 e 12 caracteres"
            else -> FieldState.Input.VALID to null
        }

        val updatedField = FieldState(
            text = password,
            status = status,
            message = message
        )

        val inputStatus = if (
            emailField.status == FieldState.Input.VALID &&
            updatedField.status == FieldState.Input.VALID &&
            confirmationField.status == FieldState.Input.VALID
        ) {
            Status.AwaitingInput.Ready
        } else {
            val errCode = listOf(
                if (emailField.status == FieldState.Input.ERROR) 1 else 0,
                if (updatedField.status == FieldState.Input.ERROR) 1 else 0,
                if (confirmationField.status == FieldState.Input.ERROR) 1 else 0
            ).joinToString(separator = "")

            inputErrorMap[errCode] ?: Status.AwaitingInput.Default
        }

        val buttonStatus = when (inputStatus) {
            Status.AwaitingInput.Ready -> ButtonState(true)
            else -> ButtonState(false)
        }

        return this.copy(
            passwordField = updatedField,
            status = inputStatus,
            createButton = buttonStatus
        )
    }

    fun updateConfirmation(confirmation: String): EmailAuthUiState {
        val (status, message) = when {
            confirmation.isEmpty() -> Pair(FieldState.Input.ERROR, "Preencha o campo confirmação")
            confirmation != passwordField.text -> Pair(FieldState.Input.ERROR, "Senha e confirmação diferentes")

            else -> Pair(FieldState.Input.VALID, null)
        }

        val updatedField = FieldState(text = confirmation, status = status, message = message)

        val inputStatus = when {
            emailField.status == FieldState.Input.VALID &&
                    passwordField.status == FieldState.Input.VALID &&
                    updatedField.status == FieldState.Input.VALID ->   Status.AwaitingInput.Ready
            else -> {
                val errCode = listOf(
                    if (emailField.status == FieldState.Input.ERROR) 1 else 0,
                    if (passwordField.status == FieldState.Input.ERROR) 1 else 0,
                    if (updatedField.status == FieldState.Input.ERROR) 1 else 0
                ).joinToString(separator = "")

                inputErrorMap[errCode] ?: Status.AwaitingInput.Default
            }
        }

        val buttonStatus = when (inputStatus) {
            Status.AwaitingInput.Ready -> ButtonState(true)
            else -> ButtonState(false)
        }

        return this.copy(
            confirmationField = updatedField,
            status = inputStatus,
            createButton = buttonStatus
        )
    }

    private val inputErrorMap = hashMapOf(
        Pair(DEFAULT, Status.AwaitingInput.Default),
        Pair(STATE_ERROR_1, Status.AwaitingInput.InputError.Email),
        Pair(STATE_ERROR_2, Status.AwaitingInput.InputError.Pass),
        Pair(STATE_ERROR_3, Status.AwaitingInput.InputError.Conf),
        Pair(STATE_ERROR_4, Status.AwaitingInput.InputError.EmailAndPass),
        Pair(STATE_ERROR_5, Status.AwaitingInput.InputError.EmailAndConf),
        Pair(STATE_ERROR_6, Status.AwaitingInput.InputError.PassAndConf),
        Pair(STATE_ERROR_7, Status.AwaitingInput.InputError.EmailPassAndConf)
    )

    sealed class Status {
        // Estado de espera do fragment.
        // Pode ou nao conter erros nos edit texts
        // que é representado por cada nivel de state.
        sealed class AwaitingInput : Status() {

            data object Default : AwaitingInput() // No Error

            sealed class InputError : AwaitingInput() {
                data object Email : AwaitingInput() // Email
                data object Pass : AwaitingInput() // Password
                data object Conf : AwaitingInput() // Confirmation
                data object EmailAndPass : AwaitingInput() // Email & Password
                data object EmailAndConf : AwaitingInput() // Email & Confirmation
                data object PassAndConf : AwaitingInput() // Password & Confirmation
                data object EmailPassAndConf : AwaitingInput() // Email & Password & Confirmation
            }

            data object Ready : AwaitingInput()
        }

        // App retornaaará ao etado inicial sem erros
        // Loading dialog será exibido
        // ViewModel tentará cadastrar novo usurio a partir do email forncido
        data object Creating : Status()

        // Email cadatrado com sucesso, o app derá navegar para a verificaão do email criado
        data object Success : Status()

        // Etado acionado quando recebo algum erro irrecuperavel e o app precisa ser fechdao
        data class Error(val uiError: UiError.Critical) : Status()
    }

    companion object {
        private const val DEFAULT = "000"
        private const val STATE_ERROR_1 = "100"
        private const val STATE_ERROR_2 = "010"
        private const val STATE_ERROR_3 = "001"
        private const val STATE_ERROR_4 = "110"
        private const val STATE_ERROR_5 = "101"
        private const val STATE_ERROR_6 = "011"
        private const val STATE_ERROR_7 = "111"
    }

}

