package com.example.truckercore.view_model.view_models.verifying_email

sealed class VerifyingEmailEvent {

    data object ResendButtonClicked: VerifyingEmailEvent()

}