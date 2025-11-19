package com.example.truckercore.layers.presentation.nav_login.view_model.verifying_email.state

sealed class VerifyingEmailStatus {

    data object Idle: VerifyingEmailStatus()

    data object WaitingForVerification : VerifyingEmailStatus()

    data object EmailVerified : VerifyingEmailStatus()

}