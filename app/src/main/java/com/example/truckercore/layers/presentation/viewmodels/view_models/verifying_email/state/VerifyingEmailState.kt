package com.example.truckercore.layers.presentation.viewmodels.view_models.verifying_email.state

import com.example.truckercore.core.classes.Email
import com.example.truckercore.domain._shared.components.TextComponent

data class VerifyingEmailState(
    val email: TextComponent = TextComponent(),
    val status: VerifyingEmailStatus = VerifyingEmailStatus.Idle
) : com.example.truckercore.presentation.viewmodels._shared._contracts.State {

    fun initialize(newEmail: Email) = updateState(newEmail = TextComponent(newEmail.value))

    fun waitingVerification() = updateState(newStatus = VerifyingEmailStatus.WaitingForVerification)

    fun verified() = updateState(newStatus = VerifyingEmailStatus.EmailVerified)

    private fun updateState(
        newEmail: TextComponent = email,
        newStatus: VerifyingEmailStatus = status
    ) = copy(email = newEmail, status = newStatus)

}