package com.example.truckercore.view_model.view_models.verifying_email.state

import com.example.truckercore._shared.classes.Email
import com.example.truckercore.view_model._shared._contracts.State

data class VerifyingEmailState(
    val components: VerifyingEmailComponents = VerifyingEmailComponents(),
    val status: VerifyingEmailStatus = VerifyingEmailStatus.Idle
) : State {

    fun initialize(email: Email) = copy(components = components.initializeEmail(email))

    fun noConnection() = updateStatus(VerifyingEmailStatus.NoConnection)

    fun waitingVerification() = updateStatus(VerifyingEmailStatus.WaitingForVerification)

    fun verified() = updateStatus(VerifyingEmailStatus.EmailVerified)

    private fun updateStatus(newStatus: VerifyingEmailStatus) = copy(status = newStatus)

}