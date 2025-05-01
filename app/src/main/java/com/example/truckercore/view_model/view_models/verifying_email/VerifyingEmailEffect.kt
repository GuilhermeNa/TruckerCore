package com.example.truckercore.view_model.view_models.verifying_email

import com.example.truckercore.model.infrastructure.app_exception.AppException

sealed class VerifyingEmailEffect {

    data class Error(val error: AppException): VerifyingEmailEffect()

}