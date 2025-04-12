package com.example.truckercore.view_model.view_models.verifying_email

import com.example.truckercore.view.sealeds.UiError

sealed class VerifyingEmailEffect {
    data object CounterReachZero : VerifyingEmailEffect()

    data object EmailVerificationSucceed : VerifyingEmailEffect()
    data class EmailVerificationFailed(val uiError: UiError) : VerifyingEmailEffect()

    data class SendEmailSucceed(val message: String) : VerifyingEmailEffect()
    data class SendEmailFailed(val uiError: UiError) : VerifyingEmailEffect()

}