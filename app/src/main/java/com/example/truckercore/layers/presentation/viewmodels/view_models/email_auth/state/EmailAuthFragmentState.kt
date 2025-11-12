package com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.state

import com.example.truckercore.core.classes.Email
import com.example.truckercore.core.classes.EmailCredential
import com.example.truckercore.layers.presentation.viewmodels.base._contracts.State

private typealias Components = com.example.truckercore.presentation.viewmodels.view_models.email_auth.uiState.EmailAuthUiComponents
private typealias Status = com.example.truckercore.presentation.viewmodels.view_models.email_auth.uiState.EmailAuthUiStatus

data class EmailAuthFragmentState(
    val components: com.example.truckercore.presentation.viewmodels.view_models.email_auth.uiState.Components = com.example.truckercore.presentation.viewmodels.view_models.email_auth.uiState.Components(),
    val status: com.example.truckercore.presentation.viewmodels.view_models.email_auth.uiState.Status = com.example.truckercore.presentation.viewmodels.view_models.email_auth.uiState.EmailAuthUiStatus.Idle
) : State {

    fun idle() = updateStatus(com.example.truckercore.presentation.viewmodels.view_models.email_auth.uiState.EmailAuthUiStatus.Idle)

    fun creating() = updateStatus(com.example.truckercore.presentation.viewmodels.view_models.email_auth.uiState.EmailAuthUiStatus.Creating)

    fun created(): com.example.truckercore.presentation.viewmodels.view_models.email_auth.uiState.EmailAuthUiState {
        val newComponents = components.disableButton()
        val newStatus =
            com.example.truckercore.presentation.viewmodels.view_models.email_auth.uiState.EmailAuthUiStatus.Created
        return copy(components = newComponents, status = newStatus)
    }

    fun updateEmail(email: String): com.example.truckercore.presentation.viewmodels.view_models.email_auth.uiState.EmailAuthUiState {
        val newComponents = components.updateEmail(email)
        return updateComponents(newComponents)
    }

    fun updatePassword(password: String): com.example.truckercore.presentation.viewmodels.view_models.email_auth.uiState.EmailAuthUiState {
        val newComponents = components.updatePassword(password)
        return updateComponents(newComponents)
    }

    fun updateConfirmation(confirmation: String): com.example.truckercore.presentation.viewmodels.view_models.email_auth.uiState.EmailAuthUiState {
        val newComponents = components.updateConfirmation(confirmation)
        return updateComponents(newComponents)
    }

    private fun updateComponents(newComponents: com.example.truckercore.presentation.viewmodels.view_models.email_auth.uiState.EmailAuthUiComponents) =
        copy(components = newComponents)

    private fun updateStatus(newStatus: com.example.truckercore.presentation.viewmodels.view_models.email_auth.uiState.EmailAuthUiStatus) = copy(status = newStatus)

    fun getCredential() = EmailCredential(
        Email.from(components.emailComponent.text),
        com.example.truckercore.core.my_lib.classes.Password.from(components.passwordComponent.text)
    )

}

