package com.example.truckercore.layers.presentation.nav_login.view_model.login

import androidx.lifecycle.viewModelScope
import com.example.truckercore.core.expressions.logEvent
import com.example.truckercore.data.infrastructure.repository.preferences.contracts.PreferencesRepository
import com.example.truckercore.domain._shared.expressions.mapResult
import com.example.truckercore.domain.view_models.login.state.LoginStateManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: com.example.truckercore.layers.presentation.viewmodels.view_models.login.LoginViewUseCase,
    private val preferencesRepository: PreferencesRepository
) : com.example.truckercore.presentation.viewmodels._shared._base.view_model.LoggerViewModel() {

    private val stateManager = LoginStateManager()
    val stateFlow get() = stateManager.stateFlow

    private val effectManager =
        com.example.truckercore.presentation.viewmodels.view_models.login.effect.LoginEffectManager()
    val effectFlow get() = effectManager.effectFlow

    //----------------------------------------------------------------------------------------------
    fun onEvent(event: com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent) {
        logEvent(this@LoginViewModel, event)
        when (event) {
            is com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.UiEvent.Typing -> handleTypingEvent(event)
            is com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.UiEvent.Click -> handleClickEvent(event)
            is com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.SystemEvent -> handleSystemEvent(event)
        }
    }

    private fun handleTypingEvent(event: com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.UiEvent.Typing) {
        when (event) {
            is com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.UiEvent.Typing.EmailField ->
                stateManager.updateComponentsOnEmailChange(event.text)

            is com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.UiEvent.Typing.PasswordField ->
                stateManager.updateComponentsOnPasswordChange(event.text)
        }
    }

    private fun handleClickEvent(event: com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.UiEvent.Click) {
        when (event) {
            com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.UiEvent.Click.Background ->
                effectManager.setCLearFocusAndHideKeyboardEffect()

            is com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.UiEvent.Click.CheckBox -> {
                stateManager.updateComponentsOnCheckBoxChange(event.isChecked)
            }

            com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.UiEvent.Click.EnterButton ->
                onEvent(com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.SystemEvent.LoginTask.Executing)

            com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.UiEvent.Click.NewAccountButton ->
                effectManager.setNavigateToNewUserEffect()

            com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.UiEvent.Click.RecoverPasswordButton ->
                effectManager.setNavigateToForgetPasswordEffect()


        }
    }

    private fun handleSystemEvent(event: com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.SystemEvent) {
        when (event) {
            com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.SystemEvent.LoginTask.Executing -> {
                stateManager.setLoadingState()
                tryToLogin()
            }

            com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.SystemEvent.LoginTask.Success -> launch {
                val isChecked = stateManager.getCheckBoxState()
                preferencesRepository.setKeepLogged(isChecked)
                effectManager.setNavigateToMainEffect()
            }

            is com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.SystemEvent.LoginTask.CriticalError ->
                effectManager.setNavigateToNotificationEffect()

            is com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.SystemEvent.LoginTask.RecoverableError -> {
                stateManager.setIdleState()
                effectManager.setShowToastEffect(event.e.message)
            }

        }
    }

    private fun tryToLogin() {
        viewModelScope.launch {
            delay(500)
            val credentials = stateManager.getCredential()
            val result = loginUseCase(credentials).mapResult(
                onSuccess = { com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.SystemEvent.LoginTask.Success },
                onRecoverableError = { com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.SystemEvent.LoginTask.RecoverableError(it) },
                onCriticalError = { com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.SystemEvent.LoginTask.CriticalError(it) }
            )
            onEvent(result)
        }
    }

}

