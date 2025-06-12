package com.example.truckercore.view_model.view_models.user_name

import androidx.lifecycle.viewModelScope
import com.example.truckercore._shared.classes.AppResult
import com.example.truckercore._shared.expressions.extractData
import com.example.truckercore._shared.expressions.mapAppResult
import com.example.truckercore.model.configs.flavor.FlavorService
import com.example.truckercore.model.infrastructure.integration.preferences.PreferencesRepository
import com.example.truckercore.model.modules.aggregation.system_access.factory.SystemAccessForm
import com.example.truckercore.model.modules.aggregation.system_access.manager.SystemAccessManager
import com.example.truckercore.model.modules.authentication.manager.AuthManager
import com.example.truckercore.view_model._shared._base.view_model.LoggerViewModel
import com.example.truckercore.view_model.view_models.user_name.effect.UserNameEffectManager
import com.example.truckercore.view_model.view_models.user_name.state.UserNameStateManager
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

/**
 * ViewModel that manages the business logic and state for the UserNameFragment.
 * This ViewModel handles the user input validation, updates the state of the fragment,
 * and manages the interaction between the fragment's UI and the business logic.
 *
 * It provides the fragment with the following:
 * - The current state of the fragment.
 * - The events triggered by the fragment.
 * - The effects that can be emitted to update the UI or navigate.
 *
 * @param authService An instance of AuthService to interact with the authentication service.
 */
class UserNameViewModel(
    private val preferences: PreferencesRepository,
    private val accessManager: SystemAccessManager,
    private val authManager: AuthManager,
    private val flavorService: FlavorService
) : LoggerViewModel() {

    private val stateManager = UserNameStateManager()
    val stateFlow get() = stateManager.stateFlow

    private val effectManager = UserNameEffectManager()
    val effectFlow get() = effectManager.effectFlow

    //----------------------------------------------------------------------------------------------
    fun onUiEvent(uiEvent: UserNameEvent.UiEvent) {
        when(uiEvent){
            UserNameEvent.UiEvent.FabCLicked ->
                onCreateSystemTaskEvent(UserNameEvent.SystemEvent.CreateSystemTask.Execute)

            is UserNameEvent.UiEvent.TextChanged -> TODO()
        }
    }

    private fun onCreateSystemTaskEvent(taskEvent: UserNameEvent.SystemEvent.CreateSystemTask) {
        when(taskEvent) {
            UserNameEvent.SystemEvent.CreateSystemTask.Execute -> TODO()
            UserNameEvent.SystemEvent.CreateSystemTask.Success -> TODO()
            UserNameEvent.SystemEvent.CreateSystemTask.CriticalError -> TODO()
            UserNameEvent.SystemEvent.CreateSystemTask.RecoverableError -> TODO()
        }
    }

    private fun handleUiEvent(event: UserNameEvent.UiEvent) {
        when (event) {
            is UserNameEvent.UiEvent.TextChanged -> stateManager.updateName(event.text)
            UserNameEvent.UiEvent.FabCLicked -> {
                stateManager.setCreatingSystemAccessState()
                createSystemAccess()
            }
        }
    }

    private fun handleSystemEvent(event: UserNameEvent.SystemEvent) {
        when (event) {
            UserNameEvent.SystemEvent.SystemCreationSuccess -> stateManager.setSuccessState()

            is UserNameEvent.SystemEvent.SystemCreationFailed -> {

            }
        }
    }

    private fun createSystemAccess() {
        viewModelScope.launch {
            val email = authManager.getUserEmail().extractData()
            val uid = authManager.getUID().extractData()
            val role = flavorService.getRole()
            val name = stateManager.getName()

            val form = SystemAccessForm(uid, role, name, email)

            val result = accessManager.createSystemAccess(form)
            val newEvent = handleResult(result)
            onEvent(newEvent)
        }
    }

    private fun handleResult(result: AppResult<Unit>): UserNameEvent =
        result.mapAppResult(
            onSuccess = { UserNameEvent.SystemEvent.SystemCreationSuccess },
            onError = { UserNameEvent.SystemEvent.SystemCreationFailed(it) }
        )

}