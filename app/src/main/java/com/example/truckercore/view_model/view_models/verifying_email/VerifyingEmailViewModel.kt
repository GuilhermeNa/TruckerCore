package com.example.truckercore.view_model.view_models.verifying_email

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore.model.infrastructure.security.authentication.errors.SendEmailVerificationErrCode
import com.example.truckercore.model.infrastructure.security.authentication.errors.SendEmailVerificationException
import com.example.truckercore.model.infrastructure.security.authentication.service.AuthService
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import com.example.truckercore.model.shared.utils.sealeds.Result
import com.example.truckercore.model.shared.utils.sealeds.mapResult
import com.example.truckercore.view.sealeds.UiFatalError
import com.example.truckercore.view.sealeds.UiNoConnectionError
import com.example.truckercore.view.sealeds.UiServerError
import com.example.truckercore.view.sealeds.UiSessionExpiredError
import com.example.truckercore.view.sealeds.UiUnknownError
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

class VerifyingEmailViewModel(private val authService: AuthService) : ViewModel() {

    private val stateManager = StateManager()
    val state get() = stateManager.fragmentState.asStateFlow()

    private val eventManager = EventManager()
    val event get() = eventManager.event.asSharedFlow()

    private val effectManager = EffectManager()
    val effect get() = effectManager.effect.asSharedFlow()

    private val counterManager = CounterManager()
    val counter get() = counterManager.counterState.asStateFlow()

    private var validationJob: Job? = null

    init {
        observeEmailValidation()
    }

    //----------------------------------------------------------------------------------------------

    private fun observeEmailValidation() {
        validationJob = viewModelScope.launch {
            authService.observeEmailValidation().collect { response ->
                effectManager.handleObserveEmailResult(response)
            }
        }
    }

    fun sendVerificationEmail() {
        viewModelScope.launch {
            val result = authService.sendVerificationEmail()
            effectManager.handleSendEmailResult(result)
        }
    }

    private fun cancelJob(job: Job?) = job?.cancel()

    fun setEffect(newEffect: VerifyingEmailEffect) = effectManager.setEffect(newEffect)

    fun setState(newState: VerifyingEmailFragState) = stateManager.setState(newState)

    fun setEvent(newEvent: VerifyingEmailEvent) = eventManager.setEvent(newEvent)

    fun startCounter() = counterManager.startCounter()

    //----------------------------------------------------------------------------------------------
    // Helper Classes
    //----------------------------------------------------------------------------------------------
    inner class CounterManager {

        val counterState = MutableStateFlow(59)
        private var counterJob: Job? = null

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

    inner class EffectManager {

        val effect = MutableSharedFlow<VerifyingEmailEffect>()

        fun setEffect(newEffect: VerifyingEmailEffect) {
            viewModelScope.launch { effect.emit(newEffect) }
        }

        fun handleSendEmailResult(result: Result<Unit>) {
            result.mapResult(
                success = { SendEmailSucceed(EMAIL_SENT_MESSAGE) },
                error = { e -> SendEmailFailed(getUiError(e)) }
            ).let { effect -> setEffect(effect) }
        }

        private fun getUiError(e: Exception) = when (e) {
            is NullFirebaseUserException -> UiFatalError(USER_NOT_FOUND_TITLE, USER_NOT_FOUND_MESSAGE)

            is SendEmailVerificationException -> {
                when (e.code) {
                    is SendEmailVerificationErrCode.NetworkError -> UiNoConnectionError()
                    is SendEmailVerificationErrCode.EmailNotFoundError -> UiSessionExpiredError()
                    is SendEmailVerificationErrCode.UnknownError -> UiUnknownError()
                    is SendEmailVerificationErrCode.UnsuccessfulTaskError -> UiServerError()
                }
            }

            else -> UiUnknownError()
        }

        fun handleObserveEmailResult(response: AppResponse<Unit>) {
            response.mapResponse(
                success = { EmailVerificationSucceed },
                error = { e -> EmailVerificationFailed(getUiError(e)) }
            )?.let { effect ->
                cancelJob(validationJob)
                setEffect(effect)
            }
        }

    }

    inner class EventManager {

        val event = MutableSharedFlow<VerifyingEmailEvent>()

        fun setEvent(newEvent: VerifyingEmailEvent) {
            viewModelScope.launch { event.emit(newEvent) }
        }

    }

    private class StateManager {

        val fragmentState: MutableStateFlow<VerifyingEmailFragState> =
            MutableStateFlow(TryingToVerify)

        fun setState(newState: VerifyingEmailFragState) {
            fragmentState.value = newState
        }

    }

    companion object {
        private const val USER_NOT_FOUND_TITLE = "Usuário não encontrado"
        private const val USER_NOT_FOUND_MESSAGE = "Não foi possível continuar com o processo de verificação."
        private const val EMAIL_SENT_MESSAGE = "Email foi enviado com sucesso."
    }

}








