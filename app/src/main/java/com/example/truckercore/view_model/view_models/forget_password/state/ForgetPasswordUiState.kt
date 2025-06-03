package com.example.truckercore.view_model.view_models.forget_password.state

import com.example.truckercore.view_model._shared._contracts.State
import com.example.truckercore.view_model._shared.components.ButtonComponent
import com.example.truckercore.view_model._shared.components.TextInputComponent
import com.example.truckercore.model.shared.utils.expressions.isEmailFormat

data class ForgetPasswordUiState(
    val passwordComponent: TextInputComponent = TextInputComponent(),
    val buttonComponent: ButtonComponent = ButtonComponent(isEnabled = false),
    val status: Status = Status.Idle
) : State {

    fun idle() = copy(status = Status.Idle)

    fun loading() = copy(status = Status.Loading, buttonComponent = ButtonComponent(isEnabled = false))

    fun updateEmail(email: String): ForgetPasswordUiState {
        val updatedEmailComponent = when {
            email.isBlank() -> TextInputComponent(text = email, isValid = false)
            email.isEmailFormat() -> TextInputComponent(text = email, isValid = true)
            else -> TextInputComponent(text = email, isValid = false, errorText = MSG_EMAIL_ERROR)
        }
        val updatedButtonComponent = getUpdatedButtonComponent(updatedEmailComponent)

        return copy(
            passwordComponent = updatedEmailComponent,
            buttonComponent = updatedButtonComponent
        )
    }

    private fun getUpdatedButtonComponent(updatedEmail: TextInputComponent) =
        if (updatedEmail.isValid) ButtonComponent(isEnabled = true)
        else ButtonComponent(isEnabled = false)

    sealed class Status {
        data object Idle : Status()
        data object Loading : Status()

        fun isLoading() = this is Loading

    }

    companion object {
        private const val MSG_EMAIL_ERROR = "Formato de email inválido"
    }

}