package com.example.truckercore.view_model.view_models.forget_password

import com.example.truckercore.view.ui_error.UiError

sealed class ForgetPasswordEvent {

    sealed class UiEvent : ForgetPasswordEvent() {
        sealed class Typing : UiEvent() {
            data class EmailText(val text: String) : UiEvent()
        }
        sealed class Click : UiEvent() {
            data object Background : UiEvent()
            data object SendButton : UiEvent()
        }
    }

    sealed class SystemEvent : ForgetPasswordEvent() {
        sealed class SendEmailTask : SystemEvent() {
            data object Executing : SendEmailTask()
            data object Success : SystemEvent()
            data object CriticalError : SystemEvent()
            data class RecoverableError(val e: UiError.Recoverable) : SystemEvent()
        }
    }

}