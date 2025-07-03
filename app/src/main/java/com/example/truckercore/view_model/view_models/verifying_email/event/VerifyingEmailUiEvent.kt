package com.example.truckercore.view_model.view_models.verifying_email.event

import com.example.truckercore.view_model._shared._contracts.Event

sealed class VerifyingEmailUiEvent: Event {
    data object OnRetryClicked : VerifyingEmailUiEvent()
    data object OnCreateAccountClicked : VerifyingEmailUiEvent()
    data object OnCheckConnectionClicked : VerifyingEmailUiEvent()

    data object OnState2TransitionCompleted : VerifyingEmailUiEvent()
    data object OnState3TransitionCompleted : VerifyingEmailUiEvent()
}