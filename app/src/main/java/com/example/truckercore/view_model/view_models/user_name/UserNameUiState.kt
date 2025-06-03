package com.example.truckercore.view_model.view_models.user_name

import com.example.truckercore._shared.classes.ButtonState
import com.example.truckercore._shared.classes.FieldState
import com.example.truckercore.view.fragments.user_name.UserNameFragment
import com.example.truckercore.view._shared.ui_error.UiError

/**
 * Sealed class to represent the different states of the [UserNameFragment].
 * Collected and handled by the [UserNameViewModel] to manage fragment interactions.
 */
data class UserNameUiState(
    val fieldState: FieldState = FieldState(),
    val fabState: ButtonState = ButtonState(false),
    val status: Status = Status.AwaitingInput
) {

    sealed class Status {
        data object AwaitingInput : Status()
        data object CreatingSystemAccess : Status()
        data object Success : Status()
        data class CriticalError(val uiError: UiError.Critical) : Status()
    }

}


