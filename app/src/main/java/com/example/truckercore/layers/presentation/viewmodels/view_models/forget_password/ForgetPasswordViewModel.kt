package com.example.truckercore.layers.presentation.viewmodels.view_models.forget_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore.domain._shared.expressions.mapResult
import com.example.truckercore.domain.view_models.forget_password.effect.ForgetPasswordEffectManager
import com.example.truckercore.domain.view_models.forget_password.state.ForgetPasswordUiStateManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ForgetPasswordViewModel(
    private val resetPasswordUseCase: com.example.truckercore.presentation.viewmodels.view_models.forget_password.ResetPasswordViewUseCase
) : ViewModel() {

    private val stateManager = ForgetPasswordUiStateManager()
    val stateFlow get() = stateManager.stateFlow

    private val effectManager = ForgetPasswordEffectManager()
    val effectFlow get() = effectManager.effectFlow

    //----------------------------------------------------------------------------------------------
    fun onEvent(event: ForgetPasswordEvent) {
        when (event) {
            is ForgetPasswordEvent.UiEvent -> handleUiEvent(event)
            is ForgetPasswordEvent.SystemEvent -> handleSystemEvent(event)
        }
    }

    private fun handleUiEvent(event: ForgetPasswordEvent.UiEvent) {
        when (event) {
            is ForgetPasswordEvent.UiEvent.Typing.EmailText ->
                stateManager.updateComponentsOnEmailChange(event.text)

            is ForgetPasswordEvent.UiEvent.Click.SendButton ->
                onEvent(ForgetPasswordEvent.SystemEvent.SendEmailTask.Executing)

            ForgetPasswordEvent.UiEvent.Click.Background ->
                effectManager.setClearKeyboardAndFocusEffect()
        }
    }

    private fun handleSystemEvent(event: ForgetPasswordEvent.SystemEvent) {
        when (event) {
            ForgetPasswordEvent.SystemEvent.SendEmailTask.Executing -> {
                stateManager.setLoadingState()
                sendRecoverEmail()
            }

            ForgetPasswordEvent.SystemEvent.SendEmailTask.Success -> {
                stateManager
                effectManager.setNavigateBackStackEffect()
            }

            ForgetPasswordEvent.SystemEvent.SendEmailTask.CriticalError -> {
                effectManager.setNavigateToNotificationEffect()
            }

            is ForgetPasswordEvent.SystemEvent.SendEmailTask.RecoverableError -> {
                stateManager.setIdleState()
                effectManager.setShowToastEffect(event.e.message)
            }

        }
    }

    private fun sendRecoverEmail() {
        viewModelScope.launch {
            delay(500)

            val email = stateManager.getEmail()
            val newEvent = resetPasswordUseCase(email).mapResult(
                onSuccess = { ForgetPasswordEvent.SystemEvent.SendEmailTask.Success },
                onCriticalError = { ForgetPasswordEvent.SystemEvent.SendEmailTask.CriticalError },
                onRecoverableError = {
                    ForgetPasswordEvent.SystemEvent.SendEmailTask.RecoverableError(it)
                }
            )

            delay(500)
            onEvent(newEvent)
        }
    }

}