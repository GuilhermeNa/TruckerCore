package com.example.truckercore.view_model.view_models.verifying_email

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore.model.infrastructure.security.authentication.service.AuthService
import com.example.truckercore.model.shared.utils.expressions.mapAppResult
import com.example.truckercore.model.shared.utils.sealeds.AppResult
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailEffect.EmailVerificationFailed
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailEffect.EmailVerificationSucceed
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailEffect.SendEmailFailed
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailEffect.SendEmailSucceed
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailFragState.TryingToVerify
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Responsibilities:
 * - Observes email verification status
 * - Triggers resend email actions
 * - Manages UI state, effects, events, and a resend countdown timer
 */
class VerifyingEmailViewModel(private val authService: AuthService) : ViewModel() {

    // State, Event, and Effect Managers -----------------------------------------------------------
    private val stateManager = StateManager()
    val state get() = stateManager.fragmentState.asStateFlow()

    private val eventManager = EventManager()
    val event get() = eventManager.event.asSharedFlow()

    private val effectManager = EffectManager()
    val effect get() = effectManager.effect.asSharedFlow()

    private val counterManager = CounterManager()
    val counter get() = counterManager.counterState.asStateFlow()

    // Observing validation job
    private var validationJob: Job? = null

    init {
        observeEmailValidation()
    }

    //----------------------------------------------------------------------------------------------
    /**
     * Starts a coroutine that continuously observes the email verification status from [authService].
     * Emits effects based on success or failure.
     *
     * Should update the [effect].
     *
     * [AppResult.Success] -> update effect to [EmailVerificationSucceed]
     *
     * [AppResult.Error] -> update effect to [EmailVerificationFailed]
     */
    private fun observeEmailValidation() {
        validationJob = viewModelScope.launch {
        /*    authService.observeEmailValidation().collect { result ->
                effectManager.handleObserveEmailResult(result)
            }*/
        }
    }

    /**
     * Initiates a resend verification email request.
     * Emits effects based on the result of the operation.
     *
     * Should update the [effect].
     *
     * [AppResult.Success] -> update effect to [SendEmailFailed]
     *
     * [AppResult.Error] -> update effect to [SendEmailSucceed]
     */
    fun sendVerificationEmail() {
        viewModelScope.launch {
            val result = authService.sendVerificationEmail()
            effectManager.handleSendEmailResult(result)
        }
    }

    /**
     * Cancels a given coroutine [job], if it's active.
     */
    private fun cancelJob(job: Job?) = job?.cancel()

    /**
     * Manually triggers an effect (UI-side event).
     */
    fun setEffect(newEffect: VerifyingEmailEffect) = effectManager.setEffect(newEffect)

    /**
     * Updates the current UI state of the fragment.
     */
    fun setState(newState: VerifyingEmailFragState) = stateManager.setState(newState)

    /**
     * Emits a user-triggered event to be observed by the Fragment.
     */
    fun setEvent(newEvent: VerifyingEmailEvent) = eventManager.setEvent(newEvent)

    /**
     * Starts the resend email countdown timer.
     */
    fun startCounter() = counterManager.startCounter()

    // =============================================================================================
    // Helper Managers
    // =============================================================================================

    /**
     * [CounterManager] manages a countdown timer (from 59 to 0) to delay resend email availability.
     */
    inner class CounterManager {

        val counterState = MutableStateFlow(59)
        private var counterJob: Job? = null

        /**
         * Starts the countdown timer if not already running.
         * Emits countdown value every second.
         */
        fun startCounter() {
            if (counterJob?.isActive == true) return

            counterJob = viewModelScope.launch {
                counterState.value = 59

                repeat(59) {
                    delay(1000)
                    counterState.value -= 1
                }

                cancelJob(counterJob)
            }
        }
    }

    /**
     * [EffectManager] handles one-time effects emitted from user actions or business logic,
     * such as showing messages or triggering navigation.
     */
    inner class EffectManager {

        val effect = MutableSharedFlow<VerifyingEmailEffect>()

        /**
         * Emits a new [VerifyingEmailEffect] to the UI.
         */
        fun setEffect(newEffect: VerifyingEmailEffect) {
            viewModelScope.launch { effect.emit(newEffect) }
        }

        /**
         * Handles the result of a resend email request and emits the corresponding effect.
         */
        fun handleSendEmailResult(result: AppResult<Unit>) =
            result.mapAppResult(
                success = { SendEmailSucceed(EMAIL_SENT_MESSAGE) },
                error = { e -> SendEmailFailed(e.errorCode) }
            ).let { effect -> setEffect(effect) }

        /**
         * Handles the result of observing email verification.
         * Cancels the observer job if email was verified or an error occurred.
         */
        fun handleObserveEmailResult(response: AppResult<Unit>) =
            response.mapAppResult(
                success = { EmailVerificationSucceed },
                error = { e -> EmailVerificationFailed(e.errorCode) }
            ).let { effect ->
                cancelJob(validationJob)
                setEffect(effect)
            }
    }

    /**
     * [EventManager] handles one-time user events such as button clicks.
     */
    inner class EventManager {

        val event = MutableSharedFlow<VerifyingEmailEvent>()

        /**
         * Emits a user-triggered event to be collected by the fragment.
         */
        fun setEvent(newEvent: VerifyingEmailEvent) {
            viewModelScope.launch { event.emit(newEvent) }
        }
    }

    /**
     * [StateManager] manages the UI state of the fragment (e.g., verifying or waiting to resend).
     */
    private class StateManager {

        val fragmentState: MutableStateFlow<VerifyingEmailFragState> =
            MutableStateFlow(TryingToVerify)

        /**
         * Sets a new UI state for the fragment.
         */
        fun setState(newState: VerifyingEmailFragState) {
            fragmentState.value = newState
        }
    }

    companion object {
        /** Message displayed when the verification email is sent successfully. */
        private const val EMAIL_SENT_MESSAGE = "Email foi enviado com sucesso."
    }

}