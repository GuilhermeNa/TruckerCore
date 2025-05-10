package com.example.truckercore.view_model.view_models.verifying_email

import com.example.truckercore.model.errors.AppExceptionOld

sealed class VerifyingEmailEffect {

    data class Error(val error: AppExceptionOld): VerifyingEmailEffect()

}