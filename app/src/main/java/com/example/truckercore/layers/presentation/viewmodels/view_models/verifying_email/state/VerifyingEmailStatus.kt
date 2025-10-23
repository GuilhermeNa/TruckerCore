package com.example.truckercore.layers.presentation.viewmodels.view_models.verifying_email.state

sealed class VerifyingEmailStatus {

    data object Idle: VerifyingEmailStatus()

    data object WaitingForVerification : VerifyingEmailStatus()

    data object EmailVerified : VerifyingEmailStatus()

}