package com.example.truckercore.view_model.view_models.verifying_email

import com.example.truckercore.model.infrastructure.app_exception.AppException

sealed class VerifyingEmailFragState {

    data object TryingToVerify : VerifyingEmailFragState()

    data object TimeOut : VerifyingEmailFragState()

    data object EmailVerified : VerifyingEmailFragState()

    data class Error(val error: AppException): VerifyingEmailFragState()

}