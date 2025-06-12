package com.example.truckercore.view_model.view_models.user_name

/**
 * Sealed class to represent various events that can be triggered in the [UserNameFragment].
 * These events are collected and handled by the [UserNameViewModel] to manage fragment interactions.
 */
sealed class UserNameEvent {

    sealed class UiEvent : UserNameEvent() {
        data class TextChanged(val text: String) : UiEvent()
        data object FabCLicked : UiEvent()
    }

    sealed class SystemEvent : UserNameEvent() {

        sealed class CreateSystemTask : SystemEvent() {
            data object Execute : CreateSystemTask()
            data object Success : CreateSystemTask()
            data object CriticalError : CreateSystemTask()
            data object RecoverableError : CreateSystemTask()
        }
    }

}