package com.example.truckercore.view_model.view_models.email_auth

sealed class EmailAuthFragState {

    data object Initial: EmailAuthFragState()

    data object Creating: EmailAuthFragState()

    data class Error(val errorMap: HashMap<EmailAuthFragError, String>): EmailAuthFragState()

    enum class EmailAuthFragError {
        InvalidName, InvalidSurname, InvalidEmail, InvalidPassword, InvalidPasswordConfirmation,

        Network, EmailAlreadyExists,

        Unknown;
    }

}