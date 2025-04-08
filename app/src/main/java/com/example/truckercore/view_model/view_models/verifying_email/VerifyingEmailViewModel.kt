package com.example.truckercore.view_model.view_models.verifying_email

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore.model.infrastructure.security.authentication.errors.NullFirebaseUserException
import com.example.truckercore.model.infrastructure.security.authentication.service.AuthService
import com.example.truckercore.model.shared.utils.sealeds.Response
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailFragState.EmailNotSend
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailFragState.EmailSent
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailFragState.ResendFunction.ResendBlocked
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailFragState.ResendFunction.ResendEnabled
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailFragState.VerifyingEmailFragError
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailFragState.VerifyingEmailFragError.Network
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailFragState.VerifyingEmailFragError.Unknown
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailFragState.VerifyingEmailFragError.UserNotFound
import com.google.firebase.FirebaseNetworkException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VerifyingEmailViewModel(
    args: VerifyingEmailReceivedArgs,
    private val authService: AuthService
) : ViewModel() {

    private val _fragmentState: MutableStateFlow<VerifyingEmailFragState> =
        MutableStateFlow(VerifyingEmailFragState.Initial)
    val fragmentState get() = _fragmentState.asStateFlow()

    private val _fragmentEvent = MutableSharedFlow<VerifyingEmailEvent>()
    val fragmentEvent get() = _fragmentEvent.asSharedFlow()

    private val _counterState = MutableStateFlow(59)
    val counterState get() = _counterState.asStateFlow()

    private var validationJob: Job? = null
    private var counterJob: Job? = null

    // Helper Classes ------------------------------------------------------------------------------
    private val stateProvider = VerifyingEmailFragStateProvider(args)

    //----------------------------------------------------------------------------------------------
    //
    //----------------------------------------------------------------------------------------------
    init {
        val initialState = stateProvider.getInitialState()
        setState(initialState)
        observeEmailValidation()
    }

    private fun observeEmailValidation() {
        validationJob = viewModelScope.launch {
            authService.observeEmailValidation().collect { response ->
                val newState = stateProvider.getStateOnObserveValidationResponse(response)
                newState?.let { ns ->
                    setState(ns)
                    if (ns.isSuccess()) cancelJob(validationJob)
                }
            }
        }
    }

    fun sendVerificationEmail() {
        viewModelScope.launch {
            val response = authService.sendVerificationEmail()
            val newState = stateProvider.getStateOnTrySendEmailResponse(response)
            setState(newState)
        }
    }

    private fun cancelJob(job: Job?) = job?.cancel()

    private fun setState(newState: VerifyingEmailFragState) {
        _fragmentState.value = newState
        if (newState.isUserWaitingAnEmail()) runCounter()
    }

    private fun runCounter() {
        counterJob = viewModelScope.launch {
            _counterState.value = 59

            repeat(59) {
                delay(1000)
                _counterState.value -= 1
            }

            val newState = stateProvider.getStateOnCounterReachZero()
            setState(newState)
            cancelJob(counterJob)
        }
    }

    fun setEvent(newEvent: VerifyingEmailEvent) {
        viewModelScope.launch {
            _fragmentEvent.emit(newEvent)
        }
    }

}

private class VerifyingEmailFragStateProvider(private val args: VerifyingEmailReceivedArgs) {

    fun getInitialState() = when (args.emailSent) {
        true -> EmailSent(ResendBlocked)
        false -> EmailNotSend
    }

    fun getStateOnCounterReachZero() = EmailSent(ResendEnabled)

    fun getStateOnTrySendEmailResponse(response: Response<Unit>) = when (response) {
        is Response.Success -> EmailSent(ResendBlocked)
        is Response.Error -> getErrorState(response.exception)
        is Response.Empty -> getErrorForEmptyResponse()
    }

    fun getStateOnObserveValidationResponse(response: Response<Unit>) = when (response) {
        is Response.Success -> VerifyingEmailFragState.Success
        is Response.Empty -> null
        is Response.Error -> getErrorState(response.exception)
    }

    private fun getErrorState(exception: Exception): VerifyingEmailFragState.Error {
        val (message: String, type: VerifyingEmailFragError) = when (exception) {
            is FirebaseNetworkException -> Pair(NETWORK_ERROR, Network)
            is NullFirebaseUserException -> Pair(USER_NOT_FOUND, UserNotFound)
            else -> Pair(UNKNOWN_ERROR, Unknown)
        }
        return VerifyingEmailFragState.Error(message, type)
    }

    private fun getErrorForEmptyResponse() = VerifyingEmailFragState.Error(UNKNOWN_ERROR, Unknown)

    companion object {
        private const val NETWORK_ERROR = "Falha de conexão. Verifique sua internet."
        private const val USER_NOT_FOUND = "Usuário não encontrado. Reinicie o aplicativo."
        private const val UNKNOWN_ERROR = "Erro desconhecido"
    }

}

