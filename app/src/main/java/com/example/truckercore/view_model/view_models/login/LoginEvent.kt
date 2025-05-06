package com.example.truckercore.view_model.view_models.login

sealed class LoginEvent {

    sealed class UiEvent: LoginEvent() {
        data class EmailFieldChanged(val text: String): UiEvent()
        data class PasswordFieldChanged(val text: String): UiEvent()
        data object EnterButtonClicked: UiEvent()
        data object NewAccountButtonClicked: UiEvent()
        data object ForgetPasswordButtonClicked: UiEvent()
    }

    sealed class SystemEvent: LoginEvent() {
        data object AuthenticationResponse: SystemEvent()
    }

}