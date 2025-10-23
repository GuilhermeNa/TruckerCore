package com.example.truckercore.layers.presentation.viewmodels.view_models.continue_register

import com.example.truckercore.core.expressions.extractData
import com.example.truckercore.core.expressions.launch
import com.example.truckercore.core.expressions.logEvent
import com.example.truckercore.data.modules.authentication.manager.AuthManager
import com.example.truckercore.domain.view_models.continue_register.effect.ContinueRegisterEffectManager
import com.example.truckercore.domain.view_models.continue_register.event.ContinueRegisterEvent
import com.example.truckercore.domain.view_models.continue_register.state.ContinueRegisterStateManager
import com.example.truckercore.domain.view_models.continue_register.use_case.ContinueRegisterViewUseCase
import kotlinx.coroutines.delay

class ContinueRegisterViewModel(
    private val authManager: AuthManager,
    private val continueRegisterViewUseCase: ContinueRegisterViewUseCase
) : com.example.truckercore.presentation.viewmodels._shared._base.view_model.LoggerViewModel() {

    private val stateManager = ContinueRegisterStateManager()
    val stateFlow = stateManager.stateFlow

    private val effectManager = ContinueRegisterEffectManager()
    val effectFlow = effectManager.effectFlow

    //----------------------------------------------------------------------------------------------
    fun initialize() {
        launch {
            delay(2000)
            val newEvent = if (authManager.thereIsLoggedUser()) {
                ContinueRegisterEvent.SystemEvent.InitializationTask.ValidRequirements
            } else {
                ContinueRegisterEvent.SystemEvent.InitializationTask.InvalidRequirements
            }

            onEvent(newEvent)
        }
    }

    fun onEvent(newEvent: ContinueRegisterEvent) {
        logEvent(this@ContinueRegisterViewModel, newEvent)
        when (newEvent) {
            is ContinueRegisterEvent.UiEvent -> handleUiEvent(newEvent)

            is ContinueRegisterEvent.SystemEvent.InitializationTask ->
                handleInitializationEvent(newEvent)

            is ContinueRegisterEvent.SystemEvent.CheckRegisterTask ->
                handleCheckRegisterEvent(newEvent)
        }
    }

    private fun handleUiEvent(newEvent: ContinueRegisterEvent.UiEvent) {
        when (newEvent) {
            ContinueRegisterEvent.UiEvent.Click.FinishRegisterButton -> {
                when (stateManager.getDirection()) {
                    com.example.truckercore.presentation.viewmodels.view_models.continue_register.state.ContinueRegisterDirection.VERIFY_EMAIL -> effectManager.setNavigateToVerifyEmailEffect()
                    com.example.truckercore.presentation.viewmodels.view_models.continue_register.state.ContinueRegisterDirection.CREATE_USER -> effectManager.setNavigateToUserNameEffect()
                    com.example.truckercore.presentation.viewmodels.view_models.continue_register.state.ContinueRegisterDirection.LOGIN -> effectManager.setNavigateToLoginEffect()
                }
            }

            ContinueRegisterEvent.UiEvent.Click.NewRegisterButton -> {
                authManager.signOut()
                effectManager.setNavigateToEmailAuthEffect()
            }

        }
    }

    private fun handleInitializationEvent(
        newEvent: ContinueRegisterEvent.SystemEvent.InitializationTask
    ) {
        when (newEvent) {
            is ContinueRegisterEvent.SystemEvent.InitializationTask.InvalidRequirements -> {
                effectManager.setShowErrorMessageEffect(NO_LOGGED_USER_UI_MSG)
                effectManager.setNavigateToLoginEffect()
            }

            is ContinueRegisterEvent.SystemEvent.InitializationTask.ValidRequirements -> {
                val email = authManager.getUserEmail().extractData()
                stateManager.updateEmailComponent(email)
                onEvent(ContinueRegisterEvent.SystemEvent.CheckRegisterTask.Execute)
            }
        }
    }

    private fun handleCheckRegisterEvent(
        newEvent: ContinueRegisterEvent.SystemEvent.CheckRegisterTask
    ) {
        when (newEvent) {
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
                verifyEmail = { com.example.truckercore.presentation.viewmodels.view_models.continue_register.state.ContinueRegisterDirection.VERIFY_EMAIL },
                userName = { com.example.truckercore.presentation.viewmodels.view_models.continue_register.state.ContinueRegisterDirection.CREATE_USER },
                complete = { com.example.truckercore.presentation.viewmodels.view_models.continue_register.state.ContinueRegisterDirection.LOGIN }
            )

            val newEvent = ContinueRegisterEvent.SystemEvent.CheckRegisterTask.Success(direction)
            onEvent(newEvent)
        }
    }

    private companion object {
        private const val NO_LOGGED_USER_UI_MSG = "Faça o login novamente"
    }

}