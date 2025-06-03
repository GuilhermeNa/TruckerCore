package com.example.truckercore.view_model.view_models.verifying_email

import com.example.truckercore._shared.classes.AppResult

sealed class  VerifyingEmailEvent {

    sealed class UiEvent : VerifyingEmailEvent() {
        data object StartVerification : UiEvent()
        data object RetryVerification : UiEvent()
    }

    sealed class SystemEvent : VerifyingEmailEvent() {
        data class TaskComplete(val result: AppResult<Unit>) : SystemEvent()
        data object CounterTimeOut : SystemEvent()
    }

}