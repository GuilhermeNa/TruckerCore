package com.example.truckercore.view_model.view_models.phone_auth

import androidx.lifecycle.ViewModel
import com.example.truckercore.model.infrastructure.security.authentication.service.AuthService
import com.example.truckercore.model.shared.utils.sealeds.Response
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class PhoneAuthSharedViewModel(
    private val authService: AuthService
) : ViewModel() {

    private var _phoneNumber: String? = null
    val phoneNumber get() = _phoneNumber!!

    private var _verificationId: String? = null
    val verificationId get() = _verificationId!!

    private var _receivedCode: String? = null
    val receivedCode get() = _receivedCode!!

    //-----

    fun storeVerificationId(newVerificationId: String) {
        _verificationId = newVerificationId
    }

    fun storePhoneNumber(newPhoneNumber: String) {
        _phoneNumber = newPhoneNumber
    }

    fun storeReceivedCode(newReceivedCode: String) {
        _receivedCode = newReceivedCode
    }

    suspend fun authenticateUser(credential: PhoneAuthCredential) =
        authService.createUserWithPhone(credential)

    suspend fun getCredentialAndAuthenticateUser(): Response<String> {
        val credential = PhoneAuthProvider.getCredential(verificationId, receivedCode)
        return authenticateUser(credential)
    }
}