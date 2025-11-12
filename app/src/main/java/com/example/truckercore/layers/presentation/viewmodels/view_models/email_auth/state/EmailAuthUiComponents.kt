package com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.state

import com.example.truckercore.domain._shared.components.ButtonComponent
import com.example.truckercore.domain._shared.components.TextInputComponent

data class EmailAuthUiComponents(
    val emailComponent: TextInputComponent = TextInputComponent(),
    val passwordComponent: TextInputComponent = TextInputComponent(),
    val confirmationComponent: TextInputComponent = TextInputComponent(),
    val createButtonComponent: ButtonComponent = ButtonComponent(isEnabled = false)
) {

    private val componentFactory = EmailAuthComponentFactory()

    fun updateEmail(email: String): EmailAuthUiComponents {
        val updatedEmail = componentFactory.email(email)
        val updatedButton = createButtonComponent(newEmail = updatedEmail)
        return copy(emailComponent = updatedEmail, createButtonComponent = updatedButton)
    }

    fun updatePassword(password: String): EmailAuthUiComponents {
        val updatedPassword = componentFactory.password(password)
        val updatedButton = createButtonComponent(newPassword = updatedPassword)
        return copy(passwordComponent = updatedPassword, createButtonComponent = updatedButton)
    }

    fun updateConfirmation(confirmation: String): EmailAuthUiComponents {
        val password = passwordComponent.text
        val updatedConfirmation = componentFactory.confirmation(confirmation, password)
        val updatedButton = createButtonComponent(newConfirmation = updatedConfirmation)
        return copy(
            confirmationComponent = updatedConfirmation,
            createButtonComponent = updatedButton
        )
    }

    private fun createButtonComponent(
        newEmail: TextInputComponent? = null,
        newPassword: TextInputComponent? = null,
        newConfirmation: TextInputComponent? = null
    ) = componentFactory.button(
        emailComponent = newEmail ?: emailComponent,
        passwordComponent = newPassword ?: passwordComponent,
        confirmationComponent = newConfirmation ?: confirmationComponent
    )

    fun disableButton(): EmailAuthUiComponents =
        this.copy(createButtonComponent = ButtonComponent(isEnabled = false))

}
