package com.example.truckercore.view_model.view_models.verifying_email.state

sealed class VerifyingEmailStatus {

    data object Idle: VerifyingEmailStatus()

    data object NoConnection: VerifyingEmailStatus()

    data object WaitingForVerification : VerifyingEmailStatus()

    data object EmailVerified : VerifyingEmailStatus()

}