package com.example.truckercore.view_model.view_models.phone_auth

sealed class PhoneAuthFragEvent {

    data class SendCodeButtonCLicked(val phoneNumber: String) : PhoneAuthFragEvent()

    data class VerifyButtonClicked(val code: String) : PhoneAuthFragEvent()

}