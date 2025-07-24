package com.example.truckercore.view_model.view_models.verifying_email.event

import com.example.truckercore._shared.classes.Email
import com.example.truckercore.view_model._shared._contracts.Event

interface VerifyingEmailEvent : Event

interface VerifyingEmailSystemEvent : VerifyingEmailEvent

interface VerifyingEmailUiEvent : VerifyingEmailEvent

// System Events --

sealed class VerifyingEmailInitializationEvent : VerifyingEmailSystemEvent {
    data class Success(val email: Email) : VerifyingEmailInitializationEvent()
    data object Error : VerifyingEmailInitializationEvent()
}

sealed class VerifyingEmailSendEmailEvent : VerifyingEmailSystemEvent {
    data object Success : VerifyingEmailSendEmailEvent()
    data object CriticalError : VerifyingEmailSendEmailEvent()
    data object NoConnection : VerifyingEmailSendEmailEvent()
}

sealed class VerifyingEmailVerificationEvent : VerifyingEmailSystemEvent {
    data object Success : VerifyingEmailVerificationEvent()
    data object Timeout : VerifyingEmailVerificationEvent()
    data object CriticalError : VerifyingEmailVerificationEvent()
}

// Ui Events --

sealed class VerifyingEmailClickEvent : VerifyingEmailUiEvent {
    data object OnRetry : VerifyingEmailClickEvent()
    data object OnCreateNewAccount : VerifyingEmailClickEvent()
    data object OnCheckConnection : VerifyingEmailClickEvent()
}

sealed class VerifyingEmailTransitionEvent : VerifyingEmailUiEvent {
    data object TransitionComplete : VerifyingEmailTransitionEvent()
}