package com.example.truckercore.layers.presentation.viewmodels.view_models.continue_register.event

import com.example.truckercore.presentation.viewmodels._shared._contracts.Event
import com.example.truckercore.presentation.viewmodels.view_models.continue_register.state.ContinueRegisterDirection

sealed class ContinueRegisterEvent :
    com.example.truckercore.presentation.viewmodels._shared._contracts.Event {

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
            data class Success(val direction: com.example.truckercore.presentation.viewmodels.view_models.continue_register.state.ContinueRegisterDirection) : CheckRegisterTask()
            data object CriticalError : CheckRegisterTask()
            data class RecoverableError(val message: String) : CheckRegisterTask()
        }
    }

}