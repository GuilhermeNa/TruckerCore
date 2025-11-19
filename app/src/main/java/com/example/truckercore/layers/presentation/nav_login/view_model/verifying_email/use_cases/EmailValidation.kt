package com.example.truckercore.layers.presentation.nav_login.view_model.verifying_email.use_cases

sealed class EmailValidation {
    data object Valid: EmailValidation()
    data object Timeout: EmailValidation()
    data object Error: EmailValidation()
}