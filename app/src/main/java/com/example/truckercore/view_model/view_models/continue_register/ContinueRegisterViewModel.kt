package com.example.truckercore.view_model.view_models.continue_register

import com.example.truckercore._shared.expressions.launch
import com.example.truckercore._shared.expressions.logEvent
import com.example.truckercore.model.modules.authentication.manager.AuthManager
import com.example.truckercore.view_model._shared._base.view_model.LoggerViewModel
import com.example.truckercore.view_model.view_models.continue_register.effect.ContinueRegisterEffectManager
import com.example.truckercore.view_model.view_models.continue_register.event.ContinueRegisterEvent
import com.example.truckercore.view_model.view_models.continue_register.state.ContinueRegisterDirection
import com.example.truckercore.view_model.view_models.continue_register.state.ContinueRegisterStateManager
import com.example.truckercore.view_model.view_models.continue_register.use_case.ContinueRegisterViewUseCase

class ContinueRegisterViewModel(
    private val authManager: AuthManager,
    private val continueRegisterViewUseCase: ContinueRegisterViewUseCase
) : LoggerViewModel() {

    private val stateManager = ContinueRegisterStateManager()
    val stateFlow = stateManager.stateFlow

    private val effectManager = ContinueRegisterEffectManager()
    val effectFlow = effectManager.effectFlow

    //----------------------------------------------------------------------------------------------
    fun initialize() {
        val newEvent = if (authManager.thereIsLoggedUser()) {
            ContinueRegisterEvent.SystemEvent.CheckRegisterTask.Execute
        } else {
            ContinueRegisterEvent.SystemEvent.InvalidState
        }

        onEvent(newEvent)
    }

    private fun onEvent(newEvent: ContinueRegisterEvent) {
        logEvent(this@ContinueRegisterViewModel, newEvent)
        when (newEvent) {
            is ContinueRegisterEvent.UiEvent -> handleUiEvent(newEvent)
            is ContinueRegisterEvent.SystemEvent -> handleSystemEvent(newEvent)
        }
    }

    private fun handleUiEvent(newEvent: ContinueRegisterEvent.UiEvent) {
        when (newEvent) {
            ContinueRegisterEvent.UiEvent.Click.FinishRegisterButton -> {
                when (stateManager.getDirection()) {
                    ContinueRegisterDirection.VERIFY_EMAIL -> effectManager.setNavigateToVerifyEmailEffect()
                    ContinueRegisterDirection.CREATE_USER -> effectManager.setNavigateToUserNameEffect()
                    ContinueRegisterDirection.LOGIN -> effectManager.setNavigateToLoginEffect()
                }
            }

            ContinueRegisterEvent.UiEvent.Click.NewRegisterButton -> {
                authManager.signOut()
                effectManager.setNavigateToEmailAuthEffect()
            }

        }
    }

    private fun handleSystemEvent(newEvent: ContinueRegisterEvent.SystemEvent) {
        when (newEvent) {
            ContinueRegisterEvent.SystemEvent.InvalidState -> {
                effectManager.setShowErrorMessageEffect(NO_LOGGED_USER_UI_MSG)
                effectManager.setNavigateToLoginEffect()
            }

            ContinueRegisterEvent.SystemEvent.CheckRegisterTask.Execute ->
                checkUserRegisterStatus()

            is ContinueRegisterEvent.SystemEvent.CheckRegisterTask.Success ->
                stateManager.setIdleState(newEvent.direction)

            ContinueRegisterEvent.SystemEvent.CheckRegisterTask.CriticalError ->
                effectManager.setNavigateToNotificationEffect()

            is ContinueRegisterEvent.SystemEvent.CheckRegisterTask.RecoverableError ->
                effectManager.setShowErrorMessageEffect(newEvent.message)

        }
    }

    private fun checkUserRegisterStatus() {
        launch {
            val direction = continueRegisterViewUseCase(
                verifyEmail = { ContinueRegisterDirection.VERIFY_EMAIL },
                userName = { ContinueRegisterDirection.CREATE_USER },
                complete = { ContinueRegisterDirection.LOGIN }
            )

            val newEvent = ContinueRegisterEvent.SystemEvent.CheckRegisterTask.Success(direction)
            onEvent(newEvent)
        }
    }

    private companion object {
        private const val NO_LOGGED_USER_UI_MSG = "Faça o login novamente"
    }

}