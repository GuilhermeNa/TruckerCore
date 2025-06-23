package com.example.truckercore.view_model.view_models.verifying_email.event

import com.example.truckercore._shared.classes.AppResult
import com.example.truckercore.view_model._shared._contracts.Event

sealed class VerifyingEmailEvent : Event {

    sealed class UiEvent : VerifyingEmailEvent() {
        data object StartVerification : UiEvent()
        data object RetryVerification : UiEvent()
    }

    sealed class SystemEvent : VerifyingEmailEvent() {

        sealed class VerificationTask : SystemEvent() {
            data object Success : VerificationTask()
            data object CriticalError : VerificationTask()
            data class RecoverableError(val message: String) : VerificationTask()
        }

        sealed class CounterTask : SystemEvent() {

        }

        data class TaskComplete(val result: AppResult<Unit>) : SystemEvent()
        data object CounterTimeOut : SystemEvent()
    }

}