package com.example.truckercore.view_model.view_models.verifying_email.state

import com.example.truckercore.view_model._shared._contracts.State
import com.example.truckercore.view_model._shared.components.TextComponent

data class VerifyingEmailState(
    val timerComponent: TextComponent,
    val status: VerifyingEmailStatus
): State {


}