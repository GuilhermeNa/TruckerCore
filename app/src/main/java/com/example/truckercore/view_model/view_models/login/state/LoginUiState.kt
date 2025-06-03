package com.example.truckercore.view_model.view_models.login.state

import com.example.truckercore._shared.classes.Email
import com.example.truckercore._shared.classes.Password
import com.example.truckercore.view_model._shared._contracts.State
import com.example.truckercore.view_model._shared.components.ButtonComponent
import com.example.truckercore.view_model._shared.components.CheckBoxComponent
import com.example.truckercore.view_model._shared.components.TextInputComponent
import com.example.truckercore.model.infrastructure.integration.auth.for_app.data.EmailCredential
import com.example.truckercore.model.shared.utils.expressions.isEmailFormat

data class LoginUiState(
    val emailComponent: TextInputComponent = TextInputComponent(),
    val passComponent: TextInputComponent = TextInputComponent(),
    val checkComponent: CheckBoxComponent = CheckBoxComponent(isChecked = true),
    val enterBtnComponent: ButtonComponent = ButtonComponent(isEnabled = false),
    val status: Status = Status.Idle
) : State {

    fun getCheckBoxState(): Boolean {
        return checkComponent.isChecked
    }

    fun getCredential() = EmailCredential(
        Email.from(emailComponent.text),
        Password.from(passComponent.text)
    )

    fun idle(): LoginUiState {
        return this.copy(status = Status.Idle)
    }

    fun loading(): LoginUiState {
        return this.copy(status = Status.Loading)
    }

    fun updateEmail(email: String): LoginUiState {
        val newEmailComponent = getUpdatedEmailComponent(email)
        val newButtonComponent = getButtonComponent(updatedEmail = newEmailComponent)
        return this.copy(emailComponent = newEmailComponent, enterBtnComponent = newButtonComponent)
    }

    fun updatePassword(password: String): LoginUiState {
        val newPasswordComponent = getUpdatedPasswordComponent(password)
        val newButtonComponent = getButtonComponent(updatedPass = newPasswordComponent)
        return this.copy(
            passComponent = newPasswordComponent,
            enterBtnComponent = newButtonComponent
        )
    }

    fun updateCheckBox(isChecked: Boolean): LoginUiState {
        return copy(checkComponent = CheckBoxComponent(isChecked))
    }

    private fun getUpdatedEmailComponent(email: String): TextInputComponent = when {
        email.isBlank() -> TextInputComponent(text = email, isValid = false)
        email.isEmailFormat() -> TextInputComponent(
            text = email, isValid = true, helperText = MSG_VALID_EMAIL
        )

        else -> TextInputComponent(text = email, isValid = false, errorText = MSG_INVALID_EMAIL)
    }

    private fun getUpdatedPasswordComponent(password: String): TextInputComponent = when {
        password.isBlank() -> TextInputComponent(text = password, isValid = false)
        password.length in 6..12 -> TextInputComponent(
            text = password, isValid = true, helperText = MSG_VALID_PASS
        )

        else -> TextInputComponent(text = password, isValid = false, errorText = MSG_INVALID_PASS)
    }

    private fun getButtonComponent(
        updatedEmail: TextInputComponent? = null,
        updatedPass: TextInputComponent? = null
    ): ButtonComponent {
        val consideredEmail = updatedEmail ?: emailComponent
        val consideredPass = updatedPass ?: passComponent

        return if (consideredPass.isValid && consideredEmail.isValid) {
            ButtonComponent(isEnabled = true)
        } else ButtonComponent(isEnabled = false)
    }

    companion object {
        private const val MSG_VALID_EMAIL = "Formato de email válido"
        private const val MSG_INVALID_EMAIL = "Formato de email inválido"

        private const val MSG_VALID_PASS = "Formato de senha válida"
        private const val MSG_INVALID_PASS = "Senha deve ter entre 6 e 12 caracteres"
    }

    sealed class Status {
        data object Idle : Status()
        data object Loading : Status()

        val isLoading get() = this is Loading
    }

    override fun toString(): String {
        return "EmailComponent(isValid: ${emailComponent.isValid}, hasError: ${emailComponent.hasError()}) | " +
                "PasswordComponent(isValid: ${passComponent.isValid}, hasError: ${passComponent.hasError()}) | " +
                "EnterButtonComponent(isEnabled: ${enterBtnComponent.isEnabled}) | " +
                "Status($status)"
    }

}