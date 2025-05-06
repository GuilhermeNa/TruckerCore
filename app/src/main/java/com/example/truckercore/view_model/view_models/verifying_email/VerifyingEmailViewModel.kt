package com.example.truckercore.view_model.view_models.verifying_email

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore.model.infrastructure.integration.preferences.PreferencesRepository
import com.example.truckercore.model.modules.authentication.service.AuthService
import com.example.truckercore.model.shared.utils.expressions.extractData
import com.example.truckercore.model.shared.utils.expressions.mapAppResult
import com.example.truckercore._utils.classes.Email
import com.example.truckercore.view_model.use_cases.CounterUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select

private typealias VerifyingState = VerifyingEmailUiState.Verifying
private typealias VerifiedState = VerifyingEmailUiState.Verified
private typealias TimeOutState = VerifyingEmailUiState.TimeOut

private typealias ErrorEffect = VerifyingEmailEffect.Error

private typealias StartEvent = VerifyingEmailEvent.UiEvent.StartVerification
private typealias RetryEvent = VerifyingEmailEvent.UiEvent.RetryVerification
private typealias TaskCompleteEvent = VerifyingEmailEvent.InternalEvent.TaskComplete
private typealias TimeOutEvent = VerifyingEmailEvent.InternalEvent.CounterTimeOut

class VerifyingEmailViewModel(
    private val preferences: PreferencesRepository,
    private val counterUseCase: CounterUseCase,
    private val authService: AuthService
) : ViewModel() {

    val counterFlow = counterUseCase.counter

    // State
    private val _state: MutableStateFlow<VerifyingEmailUiState> =
        MutableStateFlow(VerifyingState)
    val state get() = _state.asStateFlow()

    // Effect
    private val _effect = MutableSharedFlow<VerifyingEmailEffect>()
    val effect get() = _effect.asSharedFlow()


    //-----------------------------------------------------------------------------------------------
    fun onEvent(event: VerifyingEmailEvent) {
        when (event) {
            is StartEvent -> startVerification()

            is RetryEvent -> startVerification()

            is TaskCompleteEvent -> handleVerificationResult(event)

            is TimeOutEvent -> setState(TimeOutState)
        }
    }

    private fun startVerification() {
        viewModelScope.launch {
            setState(VerifyingState)

            val counterJob = async { counterUseCase.startCounter() }
            val observeJob = async { observeEmailValidation() }

            select {
                counterJob.onAwait {
                    observeJob.cancel()
                    onEvent(TimeOutEvent)
                }
                observeJob.onAwait { result ->
                    counterJob.cancel()
                    onEvent(TaskCompleteEvent(result))
                }
            }
        }
    }

    private fun handleVerificationResult(event: TaskCompleteEvent) {
        event.result.mapAppResult(
            onSuccess = {
                markEmailVerificationStepComplete()
                setState(VerifiedState)
            },
            onError = { setEffect(ErrorEffect(it)) }
        )
    }

    private fun markEmailVerificationStepComplete() {
        viewModelScope.launch {
          /*  preferences.markStepAsCompleted(RegistrationStep.EmailVerified)*/
        }
    }

    private suspend fun observeEmailValidation() = authService.observeEmailValidation()

    private fun setState(newState: VerifyingEmailUiState) {
        _state.value = newState
    }

    private fun setEffect(newEffect: VerifyingEmailEffect) {
        viewModelScope.launch {
            _effect.emit(newEffect)
        }
    }

    fun getEmail(): Email = authService.getUserEmail().extractData()

    fun resetUserRegistration() {
        viewModelScope.launch {
           /* preferences.resetUserRegistration()*/
        }
    }

}