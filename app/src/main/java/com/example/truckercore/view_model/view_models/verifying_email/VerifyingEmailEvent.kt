package com.example.truckercore.view_model.view_models.verifying_email

import com.example.truckercore.model.shared.utils.sealeds.AppResult

sealed class VerifyingEmailEvent {

    sealed class UiEvent : VerifyingEmailEvent() {
        data object StartVerification : UiEvent()
        data object RetryVerification : UiEvent()
    }

    sealed class InternalEvent : VerifyingEmailEvent() {
        data class TaskComplete(val result: AppResult<Unit>) : InternalEvent()
        data object CounterTimeOut : InternalEvent()
    }

}