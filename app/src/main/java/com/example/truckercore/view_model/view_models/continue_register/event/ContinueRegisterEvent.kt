package com.example.truckercore.view_model.view_models.continue_register.event

import com.example.truckercore.view_model._shared._contracts.Event
import com.example.truckercore.view_model.view_models.continue_register.state.ContinueRegisterDirection

sealed class ContinueRegisterEvent : Event {

    sealed class UiEvent : ContinueRegisterEvent() {
        sealed class Click : UiEvent() {
            data object FinishRegisterButton : Click()
            data object NewRegisterButton : Click()
        }

    }

    sealed class SystemEvent : ContinueRegisterEvent() {
        sealed class InitializationTask : SystemEvent() {
            data object InvalidRequirements : InitializationTask()
            data object ValidRequirements : InitializationTask()
        }

        sealed class CheckRegisterTask : SystemEvent() {
            data object Execute : CheckRegisterTask()
            data class Success(val direction: ContinueRegisterDirection) : CheckRegisterTask()
            data object CriticalError : CheckRegisterTask()
            data class RecoverableError(val message: String) : CheckRegisterTask()
        }
    }

}