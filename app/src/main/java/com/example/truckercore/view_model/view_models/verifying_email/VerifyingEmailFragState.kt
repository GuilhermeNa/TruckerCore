package com.example.truckercore.view_model.view_models.verifying_email

sealed class VerifyingEmailFragState {

    data object TryingToVerify : VerifyingEmailFragState()

    data object WaitingResend : VerifyingEmailFragState()

}
