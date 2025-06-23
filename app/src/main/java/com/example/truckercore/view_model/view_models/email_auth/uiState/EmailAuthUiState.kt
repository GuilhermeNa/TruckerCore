package com.example.truckercore.view_model.view_models.email_auth.uiState

import com.example.truckercore._shared.classes.Email
import com.example.truckercore._shared.classes.Password
import com.example.truckercore.model.infrastructure.integration.auth.for_app.data.EmailCredential
import com.example.truckercore.view_model._shared._contracts.State

private typealias Components = EmailAuthUiComponents
private typealias Status = EmailAuthUiStatus

data class EmailAuthUiState(
    val components: Components = Components(),
    val status: Status = EmailAuthUiStatus.Idle
) : State {

    fun idle() = updateStatus(EmailAuthUiStatus.Idle)

    fun creating() = updateStatus(EmailAuthUiStatus.Creating)

    fun created(): EmailAuthUiState {
        val newComponents = components.disableButton()
        val newStatus = EmailAuthUiStatus.Created
        return copy(components = newComponents, status = newStatus)
    }

    fun updateEmail(email: String): EmailAuthUiState {
        val newComponents = components.updateEmail(email)
        return updateComponents(newComponents)
    }

    fun updatePassword(password: String): EmailAuthUiState {
        val newComponents = components.updatePassword(password)
        return updateComponents(newComponents)
    }

    fun updateConfirmation(confirmation: String): EmailAuthUiState {
        val newComponents = components.updateConfirmation(confirmation)
        return updateComponents(newComponents)
    }

    private fun updateComponents(newComponents: EmailAuthUiComponents) =
        copy(components = newComponents)

    private fun updateStatus(newStatus: EmailAuthUiStatus) = copy(status = newStatus)

    fun getCredential() = EmailCredential(
        Email.from(components.emailComponent.text),
        Password.from(components.passwordComponent.text)
    )

}

