package com.example.truckercore.view_model.view_models.email_auth

import com.example.truckercore._utils.classes.contracts.Event
import com.example.truckercore.view.fragments.email_auth.EmailAuthForm

/**
 * EmailAuthFragEvent represents user-driven UI events in the EmailAuthFragment.
 * These events are triggered by user interactions (e.g., button clicks) and are consumed by the ViewModel to update state or trigger logic.
 */
sealed class EmailAuthEvent : Event {

    // Eventos relacionados a interação do usuário como clicks e toques
    sealed class UiEvent : EmailAuthEvent() {
        sealed class Touch : UiEvent() {
            data object Background : UiEvent()
        }

        sealed class Click : UiEvent() {
            data class ButtonCreate(val form: EmailAuthForm) : Click()
            data object ButtonAlreadyHaveAccount : Click()
        }

    }

    // Eventos relacionados ao sistema como validação de dados e resposta de API
    sealed class SystemEvent : EmailAuthEvent() {
        data object Success : SystemEvent()

        sealed class Failures : SystemEvent() {
            data object InputValidationError : SystemEvent()
            data object ApiResultError : SystemEvent()
        }

    }

}