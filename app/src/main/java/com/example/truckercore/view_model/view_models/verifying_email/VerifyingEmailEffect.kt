package com.example.truckercore.view_model.view_models.verifying_email

sealed class VerifyingEmailEffect {

    data class Error(val error: AppExceptionOld): VerifyingEmailEffect()

}