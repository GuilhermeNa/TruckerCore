package com.example.truckercore.view_model.view_models.user_name

import com.example.truckercore._shared.classes.ButtonState
import com.example.truckercore._shared.classes.FieldState
import com.example.truckercore._shared.classes.FullName
import com.example.truckercore.model.shared.utils.expressions.isFullNameFormat
import com.example.truckercore.view._shared.ui_error.UiError
import kotlinx.coroutines.flow.MutableStateFlow

class UserNameUiStateManager {

    private val _state: MutableStateFlow<UserNameUiState> = MutableStateFlow(UserNameUiState())
    val state get() = _state

    fun updateName(text: String) {
        val (fieldState, fabState) = when {
            text.isEmpty() -> Pair(
                FieldState(text = text, status = FieldState.Input.NEUTRAL),
                ButtonState(false)
            )

            text.isFullNameFormat() -> Pair(
                FieldState(text = text, status = FieldState.Input.VALID),
                ButtonState(true)
            )

            else -> Pair(
                FieldState(
                    text = text, status = FieldState.Input.ERROR,
                    message = "Digite nome e sobrenome."
                ),
                ButtonState(false)
            )
        }
        _state.value = _state.value.copy(fieldState = fieldState, fabState = fabState)
    }

    fun setAwaitingInputState() {
        _state.value = _state.value.copy(status = UserNameUiState.Status.AwaitingInput)
    }

    fun setCreatingSystemAccessState() {
        _state.value = _state.value.copy(status = UserNameUiState.Status.CreatingSystemAccess)
    }

    fun setSuccessState() {
        _state.value = _state.value.copy(status = UserNameUiState.Status.Success)
    }

    fun setCriticalErrorState(uiError: UiError.Critical) {
        _state.value = _state.value.copy(status = UserNameUiState.Status.CriticalError(uiError))
    }

    fun getName() = FullName.from(_state.value.fieldState.text)

}