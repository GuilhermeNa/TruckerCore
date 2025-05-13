package com.example.truckercore.view_model.view_models.forget_password

import com.example.truckercore.view.ui_error.UiError

sealed class ForgetPasswordEvent {

    sealed class UiEvent : ForgetPasswordEvent() {
        data object SendButtonClicked : UiEvent()
        data class EmailTextChange(val text: String) : UiEvent()
    }

    sealed class SystemEvent : ForgetPasswordEvent() {
        data object EmailSent : SystemEvent()
        data class EmailFailed(val uiError: UiError) : SystemEvent()
    }

}