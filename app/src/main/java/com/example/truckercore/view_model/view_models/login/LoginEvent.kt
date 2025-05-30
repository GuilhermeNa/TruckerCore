package com.example.truckercore.view_model.view_models.login

import com.example.truckercore._utils.classes.contracts.Event
import com.example.truckercore.model.errors.AppException
import com.example.truckercore.view.ui_error.UiError

sealed class LoginEvent : Event {

    sealed class UiEvent : LoginEvent() {
        data class EmailFieldChanged(val text: String) : UiEvent()
        data class PasswordFieldChanged(val text: String) : UiEvent()
        data object BackGroundCLick : UiEvent()
        data object EnterButtonClick : UiEvent()
        data object NewAccountButtonClick : UiEvent()
        data object ForgetPasswordButtonClick : UiEvent()
    }

    sealed class SystemEvent : LoginEvent() {
        sealed class LoginTask: SystemEvent() {
            data object Executing : LoginTask()
            data object Success : LoginTask()
            data class CriticalError(val e: UiError.Critical) : LoginTask()
            data class RecoverableError(val e: UiError.Recoverable): LoginTask()
        }
    }

}