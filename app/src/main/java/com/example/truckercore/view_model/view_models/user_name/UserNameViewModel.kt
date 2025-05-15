package com.example.truckercore.view_model.view_models.user_name

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore._utils.classes.AppResult
import com.example.truckercore._utils.expressions.extractData
import com.example.truckercore._utils.expressions.handleUiError
import com.example.truckercore._utils.expressions.launch
import com.example.truckercore._utils.expressions.mapAppResult
import com.example.truckercore.model.configs.flavor.FlavorService
import com.example.truckercore.model.infrastructure.integration.preferences.PreferencesRepository
import com.example.truckercore.model.modules.aggregation.system_access.factory.SystemAccessForm
import com.example.truckercore.model.modules.aggregation.system_access.manager.SystemAccessManager
import com.example.truckercore.model.modules.authentication.manager.AuthManager
import com.example.truckercore.view.ui_error.UiErrorFactory
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
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
) : ViewModel() {

    private val stateManager = UserNameUiStateManager()
    val state get() = stateManager.state.asStateFlow()

    private val effectManager = UserNameEffectManager()
    val effect get() = effectManager.effect.asSharedFlow()

    //----------------------------------------------------------------------------------------------
    fun onEvent(event: UserNameEvent) {
        when (event) {
            // Ui Events
            is UserNameEvent.UiEvent.TextChanged -> stateManager.updateName(event.text)

            UserNameEvent.UiEvent.FabCLicked -> {
                stateManager.setCreatingSystemAccessState()
                createSystemAccess()
            }

            //System Events
            UserNameEvent.SystemEvent.SystemCreationSuccess -> stateManager.setSuccessState()

            is UserNameEvent.SystemEvent.SystemCreationFailed -> {

                UiErrorFactory(event.e).handleUiError(
                    onRecoverable = {
                        stateManager.setAwaitingInputState()
                        launch { effectManager.setRecoverableErrorEffect(it) }
                    },
                    onCritical = { stateManager.setCriticalErrorState(it) }
                )
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