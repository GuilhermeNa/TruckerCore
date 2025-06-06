package com.example.truckercore.view_model.view_models.login

import com.example.truckercore.view_model._shared._contracts.Event
import com.example.truckercore.view_model._shared.helpers.ViewError

sealed class LoginEvent : Event {

    sealed class UiEvent : LoginEvent() {
        sealed class Typing: UiEvent() {
            data class EmailField(val text: String) : Typing()
            data class PasswordField(val text: String) : Typing()
        }
        sealed class Click: UiEvent() {
            data class CheckBox(val isChecked: Boolean): Click()
            data object Background : Click()
            data object EnterButton : Click()
            data object NewAccountButton : Click()
            data object RecoverPasswordButton : Click()
        }
    }

    sealed class SystemEvent : LoginEvent() {
        sealed class LoginTask: SystemEvent() {
            data object Executing : LoginTask()
            data object Success : LoginTask()
            data class CriticalError(val e: ViewError.Critical) : LoginTask()
            data class RecoverableError(val e: ViewError.Recoverable): LoginTask()
        }
    }

}