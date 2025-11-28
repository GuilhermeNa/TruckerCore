package com.example.truckercore.layers.presentation.nav_login.view_model.verifying_email

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.core.my_lib.expressions.handle
import com.example.truckercore.layers.data.base.outcome.OperationOutcome
import com.example.truckercore.layers.data.base.outcome.expressions.get
import com.example.truckercore.layers.data.base.outcome.expressions.isEmpty
import com.example.truckercore.layers.data.base.outcome.expressions.isSuccess
import com.example.truckercore.layers.data.base.outcome.expressions.isTrue
import com.example.truckercore.layers.domain.use_case.authentication.GetUserEmailUseCase
import com.example.truckercore.layers.domain.use_case.authentication.IsEmailVerifiedUseCase
import com.example.truckercore.layers.domain.use_case.authentication.SendEmailVerificationUseCase
import com.example.truckercore.layers.domain.use_case.authentication.VerifyEmailValidationUseCase
import com.example.truckercore.layers.presentation.base.CountdownUseCase
import com.example.truckercore.layers.presentation.base.managers.EffectManager
import com.example.truckercore.layers.presentation.base.managers.StateManager
import com.example.truckercore.layers.presentation.nav_login.view_model.verifying_email.effect.VerifyingEmailFragmentEffect
import com.example.truckercore.layers.presentation.nav_login.view_model.verifying_email.event.VerifyingEmailFragmentEvent
import com.example.truckercore.layers.presentation.nav_login.view_model.verifying_email.reducer.VerifyingEmailFragmentReducer
import com.example.truckercore.layers.presentation.nav_login.view_model.verifying_email.state.VerifyingEmailFragmentState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class VerifyingEmailViewModel(
    private val getUserEmailUseCase: GetUserEmailUseCase,
    private val countdownUseCase: CountdownUseCase,
    private val verifyEmailUseCase: VerifyEmailValidationUseCase,
    private val isEmailVerifiedUseCase: IsEmailVerifiedUseCase,
    private val sendEmailVerificationUseCase: SendEmailVerificationUseCase
) : ViewModel() {

    private var _email: Email? = null
    val email get() = _email

    // Job's
    private var counterJob: Job? = null
    private var verificationJob: Job? = null

    private val _counterStateFlow = MutableStateFlow(ONE_MINUTE)
    val counterFlow get() = _counterStateFlow.asStateFlow()

    private val stateManager = StateManager<VerifyingEmailFragmentState>(
        VerifyingEmailFragmentState.Initial
    )
    val stateFLow = stateManager.stateFlow

    private val effectManager = EffectManager<VerifyingEmailFragmentEffect>()
    val effectFlow = effectManager.effectFlow

    private val reducer = VerifyingEmailFragmentReducer()

    //----------------------------------------------------------------------------------------------
    fun initialize() {
        val result = getUserEmailUseCase()

        val newEvent = when {
            result.isSuccess() -> {
                _email = result.get()
                VerifyingEmailFragmentEvent.GetEmailTask.Complete
            }

            result.isEmpty() -> VerifyingEmailFragmentEvent.GetEmailTask.NotFound

            else -> VerifyingEmailFragmentEvent.GetEmailTask.Failure
        }

        onEvent(newEvent)
    }

    //----------------------------------------------------------------------------------------------
    // Event and Effect handler
    //----------------------------------------------------------------------------------------------
    fun onEvent(newEvent: VerifyingEmailFragmentEvent) {
        val result = reducer.reduce(stateManager.currentState(), newEvent)
        result.handle(stateManager::update, ::handleEffect)
    }

    private fun handleEffect(effect: VerifyingEmailFragmentEffect) {
        when {
            effect.isLaunchSendEmailTask -> sendEmailTask()

            effect.isLaunchVerifyTask -> verifyEmailTask()

            effect.isNavigation -> effectManager.trySend(effect)

            effect.isCancelVerifyTask -> cancelVerifyTask()

            else -> throw IllegalStateException()
        }
    }

    private fun cancelVerifyTask() {
        counterJob = null
        verificationJob = null
    }

    //----------------------------------------------------------------------------------------------
    // Send Email Task
    //----------------------------------------------------------------------------------------------
    private fun sendEmailTask() = viewModelScope.launch {
        val event = if (isEmailVerifiedUseCase().isTrue()) {
            VerifyingEmailFragmentEvent.VerifyEmailTask.Complete
        } else {
            sendEmailVerificationUseCase().toSendEmailEvent()
        }

        onEvent(event)
    }

    private fun OperationOutcome.toSendEmailEvent(): VerifyingEmailFragmentEvent =
        when (this) {
            OperationOutcome.Completed ->
                VerifyingEmailFragmentEvent.SendEmailTask.Complete

            is OperationOutcome.Failure ->
                if (exception.isByNetwork()) {
                    VerifyingEmailFragmentEvent.SendEmailTask.NoConnection
                } else VerifyingEmailFragmentEvent.SendEmailTask.Failure
        }

    //----------------------------------------------------------------------------------------------
    // Verify Email Task
    //----------------------------------------------------------------------------------------------
    private fun verifyEmailTask() {
        observeEmailVerificationTask()
        startCountdownTask()
    }

    private fun observeEmailVerificationTask() {
        if (verificationJob != null) return

        verificationJob = viewModelScope.launch {
            val outcome = verifyEmailUseCase()
            val event = outcome.toVerifyEmailEvent()
            onEvent(event)
        }
    }

    private fun OperationOutcome.toVerifyEmailEvent(): VerifyingEmailFragmentEvent =
        when (this) {
            OperationOutcome.Completed ->
                VerifyingEmailFragmentEvent.VerifyEmailTask.Complete

            is OperationOutcome.Failure -> if (exception.isByNetwork()) {
                VerifyingEmailFragmentEvent.VerifyEmailTask.NoConnection
            } else VerifyingEmailFragmentEvent.VerifyEmailTask.Failure

        }

    private fun startCountdownTask() {
        if (counterJob != null) return

        counterJob = viewModelScope.launch {
            countdownUseCase(ONE_MINUTE)
                .onCompletion { onEvent(VerifyingEmailFragmentEvent.Timeout) }
                .collect { _counterStateFlow.value = it }
        }
    }

    //----------------------------------------------------------------------------------------------
    private companion object {
        private const val ONE_MINUTE = 59
    }

}

