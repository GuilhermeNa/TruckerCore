package com.example.truckercore.view_model.view_models.verifying_email.state

sealed class VerifyingEmailStatus {

    data object Verifying: VerifyingEmailStatus()

    data object TimeOut: VerifyingEmailStatus()

}