package com.example.truckercore.view_model.view_models.login

sealed class LoginEvent {

    sealed class UiEvent : LoginEvent() {
        data class EmailFieldChanged(val text: String) : UiEvent()
        data class PasswordFieldChanged(val text: String) : UiEvent()
        data object BackGroundCLick : UiEvent()
        data object EnterButtonClick : UiEvent()
        data object NewAccountButtonClick : UiEvent()
        data object ForgetPasswordButtonClick : UiEvent()
    }

    sealed class SystemEvent : LoginEvent() {
        data object AuthenticationResponse : SystemEvent()
    }

}