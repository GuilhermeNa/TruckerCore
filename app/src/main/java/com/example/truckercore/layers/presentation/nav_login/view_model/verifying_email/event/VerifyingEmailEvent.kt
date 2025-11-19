package com.example.truckercore.layers.presentation.nav_login.view_model.verifying_email.event

import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.layers.presentation.base.contracts.Event

interface VerifyingEmailEvent : Event

//--------------------------------------------------------------------------------------------------
// System Events
//--------------------------------------------------------------------------------------------------
interface VerifyingEmailSystemEvent : VerifyingEmailEvent

sealed class VerifyingEmailInitializationEvent : VerifyingEmailSystemEvent {
    data class Success(val email: Email) : VerifyingEmailInitializationEvent() {
        override fun toString(): String = "InitializationEvent.Success(email=$email)"
    }
    data object Error : VerifyingEmailInitializationEvent() {
        override fun toString(): String = "InitializationEvent.Error"
    }
}

sealed class VerifyingEmailSendEmailEvent : VerifyingEmailSystemEvent {
    data object Success : VerifyingEmailSendEmailEvent() {
        override fun toString(): String = "SendEmailEvent.Success"
    }
    data object CriticalError : VerifyingEmailSendEmailEvent() {
        override fun toString(): String = "SendEmailEvent.CriticalError"
    }
    data object NoConnection : VerifyingEmailSendEmailEvent() {
        override fun toString(): String = "SendEmailEvent.NoConnection"
    }
}

sealed class VerifyingEmailVerificationEvent : VerifyingEmailSystemEvent {
    data object Success : VerifyingEmailVerificationEvent() {
        override fun toString(): String = "VerificationEvent.Success"
    }
    data object Timeout : VerifyingEmailVerificationEvent() {
        override fun toString(): String = "VerificationEvent.Timeout"
    }
    data object CriticalError : VerifyingEmailVerificationEvent() {
        override fun toString(): String = "VerificationEvent.CriticalError"
    }
}

//--------------------------------------------------------------------------------------------------
// Ui Events
//--------------------------------------------------------------------------------------------------
interface VerifyingEmailUiEvent : VerifyingEmailEvent

sealed class VerifyingEmailClickEvent : VerifyingEmailUiEvent {
    data object OnRetry : VerifyingEmailClickEvent() {
        override fun toString(): String = "ClickEvent.OnRetry"
    }
    data object OnCreateNewAccount : VerifyingEmailClickEvent() {
        override fun toString(): String = "ClickEvent.OnCreateNewAccount"
    }
}

sealed class VerifyingEmailTransitionEvent : VerifyingEmailUiEvent {
    data object TransitionComplete : VerifyingEmailTransitionEvent() {
        override fun toString(): String = "TransitionEvent.TransitionComplete"
    }
}