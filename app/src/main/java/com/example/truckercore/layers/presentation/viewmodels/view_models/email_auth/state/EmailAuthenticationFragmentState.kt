package com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.state

import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.core.my_lib.classes.EmailCredential
import com.example.truckercore.core.my_lib.classes.Password
import com.example.truckercore.layers.presentation.viewmodels.base._contracts.State

private typealias UiComponents = EmailAuthUiComponents
private typealias Status = EmailAuthUiStatus

data class EmailAuthenticationFragmentState(
    val uiComponents: UiComponents = UiComponents(),
    val status: Status = EmailAuthUiStatus.Idle
) : State {

    fun idle() = updateStatus(EmailAuthUiStatus.Idle)

    fun creating() = updateStatus(EmailAuthUiStatus.Creating)

    fun created(): EmailAuthenticationFragmentState {
        val newComponents = uiComponents.disableButton()
        val newStatus = EmailAuthUiStatus.Created
        return copy(uiComponents = newComponents, status = newStatus)
    }

    fun updateEmail(email: String): EmailAuthenticationFragmentState {
        val newComponents = uiComponents.updateEmail(email)
        return updateComponents(newComponents)
    }

    fun updatePassword(password: String): EmailAuthenticationFragmentState {
        val newComponents = uiComponents.updatePassword(password)
        return updateComponents(newComponents)
    }

    fun updateConfirmation(confirmation: String): EmailAuthenticationFragmentState {
        val newComponents = uiComponents.updateConfirmation(confirmation)
        return updateComponents(newComponents)
    }

    private fun updateComponents(newComponents: EmailAuthUiComponents) =
        copy(uiComponents = newComponents)

    private fun updateStatus(newStatus: EmailAuthUiStatus) = copy(status = newStatus)

    fun getCredential() = EmailCredential(
        Email.from(uiComponents.emailComponent.text),
        Password.from(uiComponents.passwordComponent.text)
    )

}

