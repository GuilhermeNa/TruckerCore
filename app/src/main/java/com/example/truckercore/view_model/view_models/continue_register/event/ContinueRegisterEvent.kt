package com.example.truckercore.view_model.view_models.continue_register.event

import com.example.truckercore.view_model._shared._contracts.Event

sealed class ContinueRegisterEvent: Event {

    sealed class UiEvent: ContinueRegisterEvent() {
        sealed class Click: UiEvent() {
            data object FinishRegisterButton: Click()
            data object NewRegisterButton: Click()
        }

    }

    sealed class SystemEvent: ContinueRegisterEvent() {
        sealed class CheckRegisterTask : SystemEvent() {
            data object Executing : CheckRegisterTask()
            data object Success : CheckRegisterTask()
            data object CriticalError : CheckRegisterTask()
            data class RecoverableError(val message: String) : CheckRegisterTask()
        }
    }

}