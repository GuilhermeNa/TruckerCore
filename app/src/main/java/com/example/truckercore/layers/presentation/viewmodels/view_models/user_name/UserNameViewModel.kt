package com.example.truckercore.layers.presentation.viewmodels.view_models.user_name

import androidx.lifecycle.viewModelScope
import com.example.truckercore.core.expressions.logEvent
import com.example.truckercore.data.modules.authentication.manager.AuthManager
import com.example.truckercore.domain._shared.expressions.mapResult
import com.example.truckercore.domain.view_models.user_name.event.UserNameEvent
import com.example.truckercore.domain.view_models.user_name.state.UserNameStateManager
import com.example.truckercore.domain.view_models.user_name.use_case.UserNameViewUseCase
import kotlinx.coroutines.launch

private typealias InvalidRequirements = UserNameEvent.SystemEvent.Initialization.InvalidRequirements
private typealias ExecuteCreationTask = UserNameEvent.SystemEvent.CreateSystemTask.Execute
private typealias CreationTaskSuccess = UserNameEvent.SystemEvent.CreateSystemTask.Success
private typealias CreationTaskCriticalError = UserNameEvent.SystemEvent.CreateSystemTask.CriticalError
private typealias CreationTaskRecoverableError = UserNameEvent.SystemEvent.CreateSystemTask.RecoverableError

class UserNameViewModel(
    private val authManager: AuthManager,
    private val userNameViewUseCase: UserNameViewUseCase
) : com.example.truckercore.presentation.viewmodels._shared._base.view_model.LoggerViewModel() {

    private val stateManager = UserNameStateManager()
    val stateFlow get() = stateManager.stateFlow

    private val effectManager =
        com.example.truckercore.presentation.viewmodels.view_models.user_name.effect.UserNameEffectManager()
    val effectFlow get() = effectManager.effectFlow

    //----------------------------------------------------------------------------------------------
    fun initialize() {
        if (!authManager.thereIsLoggedUser()) {
            onEvent(InvalidRequirements)
        }
    }

    fun onEvent(newEvent: UserNameEvent) {
        logEvent(this@UserNameViewModel, newEvent)
        when (newEvent) {
            is UserNameEvent.SystemEvent.Initialization -> handleInitEvent(newEvent)
            is UserNameEvent.UiEvent -> handleUiEvent(newEvent)
            is UserNameEvent.SystemEvent.CreateSystemTask -> handleCreateTaskEvent(newEvent)
        }
    }

    private fun handleInitEvent(initEvent: UserNameEvent.SystemEvent.Initialization) {
        if (initEvent is InvalidRequirements) {
            effectManager.setShowMessageEffect(USER_NOT_LOGGED_MSG)
            effectManager.setNavigateToLoginEffect()
        }
    }

    private fun handleUiEvent(uiEvent: UserNameEvent.UiEvent) {
        when (uiEvent) {
            UserNameEvent.UiEvent.FabCLicked -> onEvent(ExecuteCreationTask)

            is UserNameEvent.UiEvent.TextChanged ->
                stateManager.updateComponentsOnNameChange(uiEvent.text)
        }
    }

    private fun handleCreateTaskEvent(taskEvent: UserNameEvent.SystemEvent.CreateSystemTask) {
        when (taskEvent) {
            UserNameEvent.SystemEvent.CreateSystemTask.Execute -> {
                stateManager.setCreatingState()
                createSystemAccess()
            }

            UserNameEvent.SystemEvent.CreateSystemTask.Success -> {
                effectManager.setNavigateToMainEffect()
            }

            UserNameEvent.SystemEvent.CreateSystemTask.CriticalError -> {
                effectManager.setNavigateToNotificationEffect()
            }

            is UserNameEvent.SystemEvent.CreateSystemTask.RecoverableError -> {
                stateManager.setIdleState()
                effectManager.setShowMessageEffect(taskEvent.message)
            }
        }
    }

    private fun createSystemAccess() {
        viewModelScope.launch {
            val name = stateManager.getName()
            val newEvent = userNameViewUseCase(name).mapResult(
                onSuccess = { CreationTaskSuccess },
                onCriticalError = { CreationTaskCriticalError },
                onRecoverableError = { CreationTaskRecoverableError(it.message) }
            )
            onEvent(newEvent)
        }
    }

    private companion object {
        private const val USER_NOT_LOGGED_MSG = "Faça o Login novamente"
    }

}