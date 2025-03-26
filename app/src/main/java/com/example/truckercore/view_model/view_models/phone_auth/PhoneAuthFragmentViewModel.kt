package com.example.truckercore.view_model.view_models.phone_auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore.model.infrastructure.security.authentication.service.AuthService
import com.example.truckercore.model.shared.utils.sealeds.Response
import com.example.truckercore.view_model.enums.ErrorType
import com.example.truckercore.view_model.view_models.phone_auth.PhoneAuthFragEvent.SendCodeButtonCLicked
import com.example.truckercore.view_model.view_models.phone_auth.PhoneAuthFragState.Stage.WaitingCodeMessage
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch

class PhoneAuthFragmentViewModel: ViewModel() {

    private val _fragmentState: MutableStateFlow<PhoneAuthFragState> =
        MutableStateFlow(PhoneAuthFragState.Initial)
    val fragmentState get() = _fragmentState.asStateFlow()

    private val _fragmentEvent = MutableSharedFlow<PhoneAuthFragEvent>()
    val fragmentEvent get() = _fragmentEvent.asSharedFlow()

    fun authenticateUser(credential: PhoneAuthCredential) {
       /* viewModelScope.launch {
            when (val response = authService.createUserWithPhone(credential).single()) {
                is Response.Success -> setState(PhoneAuthFragState.Success)
                is Response.Empty -> handleAuthenticationEmptyResponse()
                is Response.Error -> handleAuthenticationErrorResponse(response)
            }
        }*/
    }

    private fun handleAuthenticationEmptyResponse() {
        //val newState = PhoneAuthFragState.Error()
        //setState(newState)
    }

    private fun handleAuthenticationErrorResponse(response: Response.Error) {
        val type = when (response.exception) {
            is FirebaseAuthInvalidUserException -> ErrorType.InvalidUserError
            is FirebaseAuthInvalidCredentialsException -> ErrorType.WeakPasswordError
            is FirebaseAuthUserCollisionException -> ErrorType.UserCollisionError
            else -> ErrorType.UnknownError
        }

        // setState(PhoneAuthFragState.Error(type))
    }

    private fun setState(newState: PhoneAuthFragState) {
        _fragmentState.value = newState
    }

    fun setEvent(newEvent: PhoneAuthFragEvent) {
        viewModelScope.launch {
            if (newEvent is SendCodeButtonCLicked) {
                setState(PhoneAuthFragState.AuthProgress(WaitingCodeMessage))
            }
            _fragmentEvent.emit(newEvent)
        }
    }

}