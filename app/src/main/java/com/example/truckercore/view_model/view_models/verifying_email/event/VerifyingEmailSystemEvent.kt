package com.example.truckercore.view_model.view_models.verifying_email.event

import com.example.truckercore._shared.classes.Email
import com.example.truckercore.view_model._shared._contracts.Event

sealed class VerifyingEmailSystemEvent: VerifyingEmailEvent {
    data class Initialize(val email: Email): VerifyingEmailSystemEvent()
    data object InitializationError: VerifyingEmailSystemEvent()

    data object EmailSentSuccess : VerifyingEmailSystemEvent()
    data object EmailSendCriticalError : VerifyingEmailSystemEvent()
    data object EmailSendFailedNoConnection : VerifyingEmailSystemEvent()
}