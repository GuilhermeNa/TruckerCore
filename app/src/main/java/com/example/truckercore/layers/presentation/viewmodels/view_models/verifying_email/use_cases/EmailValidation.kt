package com.example.truckercore.layers.presentation.viewmodels.view_models.verifying_email.use_cases

sealed class EmailValidation {
    data object Valid: EmailValidation()
    data object Timeout: EmailValidation()
    data object Error: EmailValidation()
}