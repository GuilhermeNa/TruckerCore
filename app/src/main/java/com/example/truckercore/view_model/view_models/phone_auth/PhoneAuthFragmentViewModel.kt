package com.example.truckercore.view_model.view_models.phone_auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore.model.infrastructure.integration._auth.service.AuthService
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PhoneAuthFragmentViewModel(
    private val authService: com.example.truckercore.model.infrastructure.integration._auth.service.AuthService
) : ViewModel() {

    private val _fragmentState: MutableStateFlow<PhoneAuthFragState> =
        MutableStateFlow(PhoneAuthFragState.Initial)
    val fragmentState get() = _fragmentState.asStateFlow()

    //----------------------------------------------------------------------------------------------
    //
    //---------------------------------------------------------------------------------------------

    fun authenticateUser(credential: PhoneAuthCredential) {
        viewModelScope.launch {
            authService.createUserWithPhone(credential).let { response ->
                val newState = when (response) {
                    is AppResponse.Success -> PhoneAuthFragState.Success
                    is AppResponse.Empty -> emptyResponse()
                    is AppResponse.Error -> errorResponse(response.exception)
                }
                setState(newState)
            }
        }
    }

    private fun emptyResponse() = PhoneAuthFragState.Error(
        message = "Falha ao recuperar uma resposta válida do servidor.",
        type = PhoneAuthFragState.PhoneAuthFragError.EmptyResponse
    )

    private fun errorResponse(exception: Exception) = when (exception) {
        is FirebaseNetworkException -> PhoneAuthFragState.Error(
            message = "Falha na conexão. Tente novamente em breve.",
            type = PhoneAuthFragState.PhoneAuthFragError.Network
        )

        else -> PhoneAuthFragState.Error(
            message = "Erro desconhecido",
            type = PhoneAuthFragState.PhoneAuthFragError.Unknown
        )
    }

    fun setState(newState: PhoneAuthFragState) {
        if (newState.isUserVerificationButIsAlreadySelfVerifying()) return
        else _fragmentState.value = newState
    }

    private fun PhoneAuthFragState.isUserVerificationButIsAlreadySelfVerifying() =
        this is PhoneAuthFragState.UserVerification
                && _fragmentState.value is PhoneAuthFragState.SelVerification

}