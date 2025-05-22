package com.example.truckercore.view_model.view_models.email_auth

import com.example.truckercore.view.ui_error.UiError

/**
 * EmailAuthFragState represents the UI state of the EmailAuthFragment.
 * It models the various stages the screen can be in, from initial input waiting to success or error states.
 */
sealed class EmailAuthUiState {

    // Estado de espera do fragment.
    // Pode ou nao conter erros nos edit texts
    // que é representado por cada nivel de state.
    sealed class AwaitingInput : EmailAuthUiState() {

        data object Default : AwaitingInput() // No Error

        sealed class InputError : AwaitingInput() {
            data object Status1 : AwaitingInput() // Email
            data object Status2 : AwaitingInput() // Password
            data object Status3 : AwaitingInput() // Confirmation
            data object Status4 : AwaitingInput() // Email & Password
            data object Status5 : AwaitingInput() // Email & Confirmation
            data object Status6 : AwaitingInput() // Password & Confirmation
            data object Status7 : AwaitingInput() // Email & Password & Confirmation
        }
    }

    // App retornaaará ao etado inicial sem erros
    // Loading dialog será exibido
    // ViewModel tentará cadastrar novo usurio a partir do email forncido
    data object Creating : EmailAuthUiState()

    // Email cadatrado com sucesso, o app derá navegar para a verificaão do email criado
    data object Success : EmailAuthUiState()

    // Etado acionado quando recebo algum erro irrecuperavel e o app precisa ser fechdao
    data class Error(val uiError: UiError.Critical) : EmailAuthUiState()
}

