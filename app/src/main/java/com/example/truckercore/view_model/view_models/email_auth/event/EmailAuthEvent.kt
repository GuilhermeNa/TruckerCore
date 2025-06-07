package com.example.truckercore.view_model.view_models.email_auth.event

import com.example.truckercore.view_model._shared._contracts.Event

/**
 * EmailAuthFragEvent represents user-driven UI events in the EmailAuthFragment.
 * These events are triggered by user interactions (e.g., button clicks) and are consumed by the ViewModel to update state or trigger logic.
 */
sealed class EmailAuthEvent : Event {

    sealed class UiEvent : EmailAuthEvent() {
        sealed class Click : UiEvent() {
            data object Background : Click()
            data object ButtonCreate : Click()
            data object ButtonAlreadyHaveAccount : Click()
        }

        sealed class Typing : UiEvent() {
            data class EmailTextChange(val text: String) : Typing()
            data class PasswordTextChange(val text: String) : Typing()
            data class ConfirmationTextChange(val text: String) : Typing()
        }
    }

    sealed class SystemEvent : EmailAuthEvent() {
        sealed class AuthTask : SystemEvent() {
            data object Executing : AuthTask()
            data object Success : AuthTask()
            data object CriticalError : AuthTask()
            data class RecoverableError(val message: String) : AuthTask()
        }
    }

}