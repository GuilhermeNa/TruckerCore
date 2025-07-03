package com.example.truckercore.view_model.view_models.verifying_email.use_cases

sealed class EmailValidation {
    data object Valid: EmailValidation()
    data object Timeout: EmailValidation()
    data object Error: EmailValidation()
}