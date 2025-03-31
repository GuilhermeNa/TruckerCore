package com.example.truckercore.view_model.view_models.email_auth

sealed class EmailAuthFragEvent {
    data object CreateAccountButtonCLicked: EmailAuthFragEvent()
    data object AlreadyHaveAccountButtonCLicked: EmailAuthFragEvent()
}