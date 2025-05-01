package com.example.truckercore.view_model.view_models.verifying_email

sealed class VerifyingEmailUiState {
    data object Verifying : VerifyingEmailUiState()
    data object Verified : VerifyingEmailUiState()
    data object TimeOut : VerifyingEmailUiState()
}