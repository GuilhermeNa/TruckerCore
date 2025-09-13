package com.example.truckercore.view_model.view_models.verifying_email.state

import com.example.truckercore._shared.classes.Email
import com.example.truckercore.view_model._shared._contracts.State
import com.example.truckercore.view_model._shared.components.TextComponent

data class VerifyingEmailState(
    val email: TextComponent = TextComponent(),
    val status: VerifyingEmailStatus = VerifyingEmailStatus.Idle
) : State {

    fun initialize(newEmail: Email) = updateState(newEmail = TextComponent(newEmail.value))

    fun waitingVerification() = updateState(newStatus = VerifyingEmailStatus.WaitingForVerification)

    fun verified() = updateState(newStatus = VerifyingEmailStatus.EmailVerified)

    private fun updateState(
        newEmail: TextComponent = email,
        newStatus: VerifyingEmailStatus = status
    ) = copy(email = newEmail, status = newStatus)

}