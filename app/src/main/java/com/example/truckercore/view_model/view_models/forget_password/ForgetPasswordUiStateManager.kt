package com.example.truckercore.view_model.view_models.forget_password

import com.example.truckercore._utils.classes.ButtonState
import com.example.truckercore._utils.classes.Email
import com.example.truckercore._utils.classes.FieldState
import com.example.truckercore._utils.classes.Input
import com.example.truckercore.model.shared.utils.expressions.isEmailFormat
import com.example.truckercore.view.ui_error.UiError
import kotlinx.coroutines.flow.MutableStateFlow

class ForgetPasswordUiStateManager {

    private val _uiState: MutableStateFlow<ForgetPasswordUiState> =
        MutableStateFlow(ForgetPasswordUiState())
    val uiState get() = _uiState

    fun setAwaitingInputState() {
        _uiState.value = _uiState.value.copy(status = ForgetPasswordUiState.Status.AwaitingInput)
    }

    fun setSuccessState() {
        _uiState.value = _uiState.value.copy(
            status = ForgetPasswordUiState.Status.Success,
            buttonState = ButtonState(false)
        )
    }

    fun setSendingEmailState() {
        _uiState.value = _uiState.value.copy(status = ForgetPasswordUiState.Status.SendingEmail)
    }

    fun setCriticalErrorState(uiError: UiError.Critical) {
        _uiState.value =
            _uiState.value.copy(status = ForgetPasswordUiState.Status.CriticalError(uiError))
    }

    fun updateEmail(text: String) {
        val (fieldState, buttonState) = when {
            text.isEmpty() -> Pair(
                FieldState(text, Input.NEUTRAL),
                ButtonState(isEnabled = false)
            )

            text.isEmailFormat() -> Pair(
                FieldState(text, Input.VALID),
                ButtonState(isEnabled = true)
            )

            else -> Pair(
                FieldState(
                    text = text,
                    validation = Input.ERROR,
                    stateMessage = "Formato de email inválido."
                ),
                ButtonState(isEnabled = false)
            )
        }
        _uiState.value = _uiState.value.copy(emailField = fieldState, buttonState = buttonState)
    }

    fun getEmail(): Email {
        return Email.from(_uiState.value.emailField.text)
    }

}
