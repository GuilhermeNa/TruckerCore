package com.example.truckercore.view_model.view_models.verifying_email

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore.model.modules.authentication.service.AuthService
import com.example.truckercore.model.shared.utils.expressions.extractData
import com.example.truckercore.model.shared.utils.expressions.mapAppResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private typealias TryingToVerifyState = VerifyingEmailFragState.TryingToVerify
private typealias EmailVerifiedState = VerifyingEmailFragState.EmailVerified
private typealias TimeOutState = VerifyingEmailFragState.TimeOut
private typealias ErrorState = VerifyingEmailFragState.Error

class VerifyingEmailViewModel(private val authService: AuthService) : ViewModel() {

    private val _email by lazy { authService.getUserEmail().extractData() }
    val email get() = _email.value

    // State
    private val _state: MutableStateFlow<VerifyingEmailFragState> =
        MutableStateFlow(TryingToVerifyState)
    val state get() = _state.asStateFlow()

    // Counter
    private val _counter = MutableStateFlow(60)
    val counter get() = _counter.asStateFlow()
    private var counterJob: Job? = null

    init {
        verifyEmail()
    }

    private fun verifyEmail() {
        setState(TryingToVerifyState)
        viewModelScope.launch {
            val result = authService.observeEmailValidation()
            result.mapAppResult(
                onSuccess = { EmailVerifiedState },
                onError = { ErrorState(it) }
            ).let { newState -> setState(newState) }
        }
    }

    private fun startCounter() {
        if (counterJob?.isActive == true) return

        counterJob = viewModelScope.launch {
            for (vle in 60 downTo 0) {
                _counter.value = vle
                delay(1000)
            }
            setState(TimeOutState)
        }.also { job ->
            job.invokeOnCompletion {
                counterJob = null
            }
        }

    }

    private fun setState(newState: VerifyingEmailFragState) {
        onStateChanged(newState)
        _state.value = newState
    }

    private fun onStateChanged(stateChanged: VerifyingEmailFragState) {
        if (stateChanged is TryingToVerifyState) startCounter()
        else cancelCounterJob()
    }

    private fun cancelCounterJob() {
        if (counterJob?.isActive == true) {
            counterJob?.cancel()
        }
    }

}