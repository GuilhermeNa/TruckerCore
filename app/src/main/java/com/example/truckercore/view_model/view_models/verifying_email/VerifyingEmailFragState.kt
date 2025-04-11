package com.example.truckercore.view_model.view_models.verifying_email

sealed class VerifyingEmailFragState {

    data object Initial : VerifyingEmailFragState()

    data class EmailSent(val resendType: ResendFunction) : VerifyingEmailFragState()

    data object EmailNotSend: VerifyingEmailFragState()

    data object Success : VerifyingEmailFragState()

    data class Error(
        val message: String,
        val type: VerifyingEmailFragError
    ): VerifyingEmailFragState()

    //----------------------------------------------------------------------------------------------

    enum class ResendFunction { ResendBlocked, ResendEnabled }

    enum class VerifyingEmailFragError { NetworkError, UserNotFoundError, Unknown }

    fun isUserWaitingAnEmail(): Boolean = when (this) {
        is EmailSent -> resendType == ResendFunction.ResendBlocked
        else -> false
    }

    fun isSuccess() = this is Success

}