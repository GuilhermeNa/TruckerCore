package com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.state

import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.core.my_lib.classes.EmailCredential
import com.example.truckercore.core.my_lib.classes.Password
import com.example.truckercore.layers.presentation.viewmodels.base._contracts.State

private typealias UiComponents = EmailAuthUiComponents
private typealias Status = EmailAuthUiStatus

data class EmailAuthenticationFragmentState(
    val uiComponents: UiComponents = UiComponents(),
    val status: Status = EmailAuthUiStatus.WaitingInput
) : State {

    fun waitingInput() = copy(status = EmailAuthUiStatus.WaitingInput)

    fun readyToCreate() = copy(status = EmailAuthUiStatus.ReadyToCreate)

    fun creating() = copy(status = EmailAuthUiStatus.Creating)

    fun updateEmail(email: String): EmailAuthenticationFragmentState {
        val newComponents = uiComponents.updateEmail(email)
        return copy(uiComponents = newComponents)
    }

    fun updatePassword(password: String): EmailAuthenticationFragmentState {
        val newComponents = uiComponents.updatePassword(password)
        return copy(uiComponents = newComponents)
    }

    fun updateConfirmation(confirmation: String): EmailAuthenticationFragmentState {
        val newComponents = uiComponents.updateConfirmation(confirmation)
        return copy(uiComponents = newComponents)
    }

    fun isReadyToCreate(): Boolean {
        //TODO(adicionar verificação se o ready o create pode ser emitido ou nao)
    }

    fun getCredential() = EmailCredential(
        Email.from(uiComponents.emailComponent.text),
        Password.from(uiComponents.passwordComponent.text)
    )

}

