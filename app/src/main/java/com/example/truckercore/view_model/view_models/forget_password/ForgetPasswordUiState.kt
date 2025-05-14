package com.example.truckercore.view_model.view_models.forget_password

import com.example.truckercore._utils.classes.ButtonState
import com.example.truckercore._utils.classes.FieldState
import com.example.truckercore.view.ui_error.UiError

data class ForgetPasswordUiState(
    val emailField: FieldState = FieldState(),
    val buttonState: ButtonState = ButtonState(isEnabled = false),
    val status: Status = Status.AwaitingInput
) {

    sealed class Status {
        data object AwaitingInput : Status()
        data object Success : Status()
        data object SendingEmail : Status()
        data class CriticalError(val uiError: UiError.Critical) : Status()
    }

    fun isSendingEmail() = status == Status.SendingEmail

    fun successMessage() = "Email enviado com sucesso."

}