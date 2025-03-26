package com.example.truckercore.view_model.view_models.phone_auth

sealed class PhoneAuthFragState {

    data object Initial : PhoneAuthFragState()

    data class AuthProgress(
        val stage: Stage
    ): PhoneAuthFragState()

    enum class Stage {
        WaitingUserTypePhone,
        WaitingCodeMessage,
        WrongCodeSent,
        Authenticating
    }

    data object Success : PhoneAuthFragState()

    /*data class Error(*//*val type: AppError*//*) : PhoneAuthFragState()*/

}



