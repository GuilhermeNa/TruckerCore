package com.example.truckercore.view_model.view_models.verifying_email

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore._utils.classes.Email
import com.example.truckercore._utils.expressions.extractData
import com.example.truckercore._utils.expressions.mapAppResult
import com.example.truckercore.model.modules.authentication.manager.AuthManager
import com.example.truckercore.view_model.use_cases.CounterUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select

private typealias VerifyingState = VerifyingEmailUiState.Verifying
private typealias VerifiedState = VerifyingEmailUiState.Verified
private typealias TimeOutState = VerifyingEmailUiState.TimeOut


private typealias StartEvent = VerifyingEmailEvent.UiEvent.StartVerification
private typealias RetryEvent = VerifyingEmailEvent.UiEvent.RetryVerification
private typealias TaskCompleteEvent = VerifyingEmailEvent.SystemEvent.TaskComplete
private typealias TimeOutEvent = VerifyingEmailEvent.SystemEvent.CounterTimeOut

class VerifyingEmailViewModel(
    private val counterUseCase: CounterUseCase,
    private val authService: AuthManager
) : ViewModel() {

    val counterFlow = counterUseCase.counter

    // State
    private val _state: MutableStateFlow<VerifyingEmailUiState> =
        MutableStateFlow(VerifyingState)
    val state get() = _state.asStateFlow()

    //-----------------------------------------------------------------------------------------------
    init {
        onEvent(VerifyingEmailEvent.UiEvent.StartVerification)
    }

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
            val observeJob = async { authService.observeEmailValidation() }

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
                 setState(VerifiedState)
             },
             onError = {
                 setState(VerifyingEmailUiState.ApiError)
             }
         )
    }

    private fun setState(newState: VerifyingEmailUiState) {
        _state.value = newState
    }

    fun getEmail(): Email = authService.getUserEmail().extractData()

}