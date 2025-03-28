package com.example.truckercore.view_model.view_models.phone_auth

import com.google.firebase.auth.PhoneAuthCredential

sealed class PhoneAuthFragState {

    data object Initial : PhoneAuthFragState()

    data class UserRequestedCode(val phoneNumber: String): PhoneAuthFragState()

    data class SelVerification(val credential: PhoneAuthCredential): PhoneAuthFragState()

    data object UserVerification : PhoneAuthFragState()

    data object Success: PhoneAuthFragState()

    data class Error(
        val message: String,
        val type: PhoneAuthFragError
    ) : PhoneAuthFragState()

    enum class PhoneAuthFragError { Network, RequestLimit, Unknown, EmptyResponse }

}



