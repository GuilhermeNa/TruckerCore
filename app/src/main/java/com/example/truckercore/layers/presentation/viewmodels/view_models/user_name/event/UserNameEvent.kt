package com.example.truckercore.layers.presentation.viewmodels.view_models.user_name.event

import com.example.truckercore.presentation.viewmodels._shared._contracts.Event

/**
 * Sealed class to represent various events that can be triggered in the [UserNameFragment].
 * These events are collected and handled by the [UserNameViewModel] to manage fragment interactions.
 */
sealed class UserNameEvent:
    com.example.truckercore.presentation.viewmodels._shared._contracts.Event {

    sealed class UiEvent : UserNameEvent() {
        data class TextChanged(val text: String) : UiEvent()
        data object FabCLicked : UiEvent()
    }

    sealed class SystemEvent : UserNameEvent() {

        sealed class Initialization: SystemEvent() {
            data object InvalidRequirements: Initialization()
        }

        sealed class CreateSystemTask : SystemEvent() {
            data object Execute : CreateSystemTask()
            data object Success : CreateSystemTask()
            data object CriticalError : CreateSystemTask()
            data class RecoverableError(val message: String) : CreateSystemTask()
        }
    }

}