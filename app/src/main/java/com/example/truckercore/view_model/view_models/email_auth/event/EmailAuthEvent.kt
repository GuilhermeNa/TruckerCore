package com.example.truckercore.view_model.view_models.email_auth.event

import com.example.truckercore._utils.classes.contracts.Event

/**
 * EmailAuthFragEvent represents user-driven UI events in the EmailAuthFragment.
 * These events are triggered by user interactions (e.g., button clicks) and are consumed by the ViewModel to update state or trigger logic.
 */
sealed class EmailAuthEvent : Event {

    // Eventos relacionados a interação do usuário como clicks e toques
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

    // Eventos relacionados ao sistema como validação de dados e resposta de API
    sealed class SystemEvent : EmailAuthEvent() {
        data object Success : SystemEvent()

        sealed class Failure : SystemEvent() {
            data object InputValidationError : SystemEvent()

            data object ApiResultError : SystemEvent()
        }

    }

}