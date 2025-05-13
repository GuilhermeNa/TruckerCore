package com.example.truckercore.view_model.view_models.forget_password

import com.example.truckercore._utils.classes.ButtonState
import com.example.truckercore._utils.classes.Email
import com.example.truckercore._utils.classes.FieldState
import com.example.truckercore.view.ui_error.UiError

data class ForgetPasswordUiState(
    val email: FieldState = FieldState(),
    val isButtonEnabled: ButtonState = ButtonState(isEnabled = false),
    val status: Status = Status.AwaitingInput
) {

    sealed class Status {
        data object AwaitingInput : Status()
        data object Success : Status()
        data object SendingEmail : Status()
        data class Error(val uiError: UiError.Critical) : Status()
    }

    fun getEmail(): Email {
        return Email.from(email.text)
    }

}