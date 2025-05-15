package com.example.truckercore.view_model.view_models.login

import com.example.truckercore._utils.classes.AppResult

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
        data class AuthenticationResult(val result: AppResult<Unit>) : SystemEvent()
        data object UserRegistered: SystemEvent()
        data object UserAwaitingRegister: SystemEvent()
        data class AuthError(val error: AppExceptionOld): SystemEvent()
    }

}