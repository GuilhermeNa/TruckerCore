package com.example.truckercore.view_model.view_models.email_auth

sealed class EmailAuthFragState {

    data object WaitingInput: EmailAuthFragState()
    // Estado inicial do aplicativo. Quando ele é aberto

    data object Creating: EmailAuthFragState()
    // Quando o bota de criar conta é clicado

    data class Success(val type: EmailAuthFragSuccess): EmailAuthFragState()

    enum class EmailAuthFragSuccess {
        UserCreatedAndEmailSent, UserCreatedAndEmailFailed
    }

    data class Error(val errorMap: HashMap<EmailAuthFragError, String>): EmailAuthFragState()

    enum class EmailAuthFragError {
        InvalidEmail, InvalidPassword, InvalidPasswordConfirmation,

        Network, Unknown;
    }

}