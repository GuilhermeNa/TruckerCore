package com.example.truckercore.layers.presentation.login.view_model.verifying_email

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.core.my_lib.expressions.handle
import com.example.truckercore.core.my_lib.expressions.isByNetwork
import com.example.truckercore.layers.data.base.outcome.OperationOutcome
import com.example.truckercore.core.my_lib.expressions.get
import com.example.truckercore.core.my_lib.expressions.isEmpty
import com.example.truckercore.core.my_lib.expressions.isSuccess
import com.example.truckercore.core.my_lib.expressions.isTrue
import com.example.truckercore.layers.domain.use_case.authentication.GetUserEmailUseCase
import com.example.truckercore.layers.domain.use_case.authentication.IsEmailVerifiedUseCase
import com.example.truckercore.layers.domain.use_case.authentication.SendEmailVerificationUseCase
import com.example.truckercore.layers.domain.use_case.authentication.VerifyEmailValidationUseCase
import com.example.truckercore.layers.domain.use_case._common.CountdownUseCase
import com.example.truckercore.layers.presentation.base.managers.EffectManager
import com.example.truckercore.layers.presentation.base.managers.StateManager
import com.example.truckercore.layers.presentation.login.view_model.verifying_email.helpers.VerifyingEmailFragmentEffect
import com.example.truckercore.layers.presentation.login.view_model.verifying_email.helpers.VerifyingEmailFragmentEvent
import com.example.truckercore.layers.presentation.login.view_model.verifying_email.helpers.VerifyingEmailFragmentReducer
import com.example.truckercore.layers.presentation.login.view_model.verifying_email.helpers.VerifyingEmailFragmentState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for orchestrating the email verification flow.
 *
 * It coordinates:
 *  - fetching the user email
 *  - sending email verification links
 *  - checking whether the email has been verified
 *  - handling countdown timers for repeated verification attempts
 *  - mapping business outcomes into UI states and one-shot effects
 *
 * The ViewModel exposes immutable [StateFlow] instances for UI state and timer updates,
 * and an immutable [SharedFlow] for navigation and non-UI-state-driven behaviors.
 *
 * This class delegates state transitions to a reducer [VerifyingEmailFragmentReducer],
 * while state and effects are stored via [StateManager] and [EffectManager].
 */
class VerifyingEmailViewModel(
    private val getUserEmailUseCase: GetUserEmailUseCase,
    private val countdownUseCase: CountdownUseCase,
    private val verifyEmailUseCase: VerifyEmailValidationUseCase,
    private val isEmailVerifiedUseCase: IsEmailVerifiedUseCase,
    private val sendEmailVerificationUseCase: SendEmailVerificationUseCase
) : ViewModel() {

    // ---------------------------------------------------------------------------------------------
    // User Email
    // ---------------------------------------------------------------------------------------------
    private var _email: Email? = null
    val email get() = _email

    // ---------------------------------------------------------------------------------------------
    // Jobs controlling long-running asynchronous tasks
    // ---------------------------------------------------------------------------------------------
    /** Tracks the countdown coroutine job. Prevents launching multiple timers. */
    private var counterJob: Job? = null

    /** Tracks the email verification polling job. Ensures only one verification task runs. */
    private var verificationJob: Job? = null

    // ---------------------------------------------------------------------------------------------
    // Timer / Countdown State
    // ---------------------------------------------------------------------------------------------
    /**
     * Backing flow for the countdown timer, storing the remaining seconds.
     *
     * The value decreases from [ONE_MINUTE] down to zero as emitted by the
     * [countdownUseCase]. The flow is initialized with the maximum countdown value
     * and is updated every second while the countdown job is running.
     *
     * UI components observe the public [counterFlow] to render progress updates
     * (e.g., a circular progress bar or a numeric timer).
     */
    private val _counterStateFlow = MutableStateFlow(ONE_MINUTE)
    val counterFlow get() = _counterStateFlow.asStateFlow()

    // ---------------------------------------------------------------------------------------------
    // UI State and Effect Management
    // ---------------------------------------------------------------------------------------------
    /**
     * State manager for screen UI state.
     * The screen starts in [VerifyingEmailFragmentState.Initial].
     */
    private val stateManager =
        StateManager<VerifyingEmailFragmentState>(VerifyingEmailFragmentState.Initial)
    val stateFLow = stateManager.stateFlow

    /**
     * Effect manager for one-shot events such as navigation and toast/error messages.
     */
    private val effectManager = EffectManager<VerifyingEmailFragmentEffect>()
    val effectFlow = effectManager.effectFlow

    /** Handles transitions between state + event → new state/effect. */
    private val reducer = VerifyingEmailFragmentReducer()

    // ---------------------------------------------------------------------------------------------
    // Initialization
    // ---------------------------------------------------------------------------------------------
    /**
     * Initializes the view model by retrieving the user's email.
     * Maps the domain result into a corresponding UI event.
     */
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

    // ---------------------------------------------------------------------------------------------
    // Event Dispatcher
    // ---------------------------------------------------------------------------------------------
    /**
     * Receives UI events or internal events and delegates the transition logic to the reducer.
     *
     * @param newEvent The event to process.
     */
    fun onEvent(newEvent: VerifyingEmailFragmentEvent) {
        val result = reducer.reduce(stateManager.currentState(), newEvent)
        result.handle(stateManager::update, ::handleEffect)
    }

    /**
     * Processes one-shot effects generated by the reducer.
     */
    private fun handleEffect(effect: VerifyingEmailFragmentEffect) {
        when {
            effect.isLaunchSendEmailTask -> sendEmailTask()
            effect.isLaunchVerifyTask -> verifyEmailTask()
            effect.isCancelVerifyTask -> cancelVerifyTask()
            effect.isNavigation -> effectManager.trySend(effect)
            else -> throw IllegalStateException("Unhandled effect: $effect")
        }
    }

    /**
     * Cancels both verification and countdown tasks when required by the reducer.
     */
    private fun cancelVerifyTask() {
        counterJob?.cancel()
        counterJob = null

        verificationJob?.cancel()
        verificationJob = null
    }

    // ---------------------------------------------------------------------------------------------
    // Send Email Verification Task
    // ---------------------------------------------------------------------------------------------

    /**
     * Sends an email verification link to the user unless the email is already verified.
     * Emits a corresponding UI event based on the domain operation result.
     */
    private fun sendEmailTask() = viewModelScope.launch {
        val event = if (isEmailVerifiedUseCase().isTrue()) {
            VerifyingEmailFragmentEvent.VerifyEmailTask.Complete
        } else {
            sendEmailVerificationUseCase().toSendEmailEvent()
        }

        onEvent(event)
    }

    /**
     * Maps a domain [OperationOutcome] to a UI event specifically for the "send email" context.
     */
    private fun OperationOutcome.toSendEmailEvent(): VerifyingEmailFragmentEvent =
        when (this) {
            OperationOutcome.Completed ->
                VerifyingEmailFragmentEvent.SendEmailTask.Complete

            is OperationOutcome.Failure ->
                if (exception.isByNetwork()) {
                    VerifyingEmailFragmentEvent.SendEmailTask.NoConnection
                } else {
                    VerifyingEmailFragmentEvent.SendEmailTask.Failure
                }
        }

    // ---------------------------------------------------------------------------------------------
    // Verify Email Task (Polling + Countdown)
    // ---------------------------------------------------------------------------------------------
    /**
     * Starts the verification process by:
     *  - launching the email verification polling job
     *  - starting the countdown timer
     */
    private fun verifyEmailTask() {
        observeEmailVerificationTask()
        startCountdownTask()
    }

    /**
     * Observes the result of the email verification process.
     * Ensures that only one verification job can run at a time.
     */
    private fun observeEmailVerificationTask() {
        if (verificationJob != null) return

        verificationJob = viewModelScope.launch {
            val outcome = verifyEmailUseCase()
            val event = outcome.toVerifyEmailEvent()
            onEvent(event)
        }
    }

    /**
     * Maps a domain [OperationOutcome] to a UI event specifically for the verification task.
     */
    private fun OperationOutcome.toVerifyEmailEvent(): VerifyingEmailFragmentEvent =
        when (this) {
            OperationOutcome.Completed ->
                VerifyingEmailFragmentEvent.VerifyEmailTask.Complete

            is OperationOutcome.Failure ->
                if (exception.isByNetwork()) {
                    VerifyingEmailFragmentEvent.VerifyEmailTask.NoConnection
                } else {
                    VerifyingEmailFragmentEvent.VerifyEmailTask.Failure
                }
        }

    /**
     * Starts the countdown timer for re-sending verification emails.
     * Launches only once until the flow completes or is cancelled.
     */
    private fun startCountdownTask() {
        if (counterJob != null) return

        counterJob = viewModelScope.launch {
            countdownUseCase(ONE_MINUTE)
                .onCompletion {
                    onEvent(VerifyingEmailFragmentEvent.Timeout)
                }
                .collect { remainingSeconds ->
                    _counterStateFlow.value = remainingSeconds
                }
        }
    }

    // ---------------------------------------------------------------------------------------------
    // Constants
    // ---------------------------------------------------------------------------------------------
    private companion object {
        /**
         * Countdown duration for email verification operations.
         * Value is in seconds (59 = 1 minute minus 1 for inclusive counting).
         */
        private const val ONE_MINUTE = 59
    }

}

