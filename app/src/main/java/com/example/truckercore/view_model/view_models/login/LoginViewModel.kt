package com.example.truckercore.view_model.view_models.login

import androidx.lifecycle.viewModelScope
import com.example.truckercore._shared.expressions.launch
import com.example.truckercore._shared.expressions.logEvent
import com.example.truckercore.model.infrastructure.integration.preferences.PreferencesRepository
import com.example.truckercore.view_model._shared._base.view_model.LoggerViewModel
import com.example.truckercore.view_model._shared.expressions.mapResult
import com.example.truckercore.view_model.view_models.login.effect.LoginEffectManager
import com.example.truckercore.view_model.view_models.login.state.LoginStateManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginViewUseCase,
    private val preferencesRepository: PreferencesRepository
) : LoggerViewModel() {

    private val stateManager = LoginStateManager()
    val stateFlow get() = stateManager.stateFlow

    private val effectManager = LoginEffectManager()
    val effectFlow get() = effectManager.effectFlow

    //----------------------------------------------------------------------------------------------
    fun onEvent(event: LoginEvent) {
        logEvent(this@LoginViewModel, event)
        when (event) {
            is LoginEvent.UiEvent.Typing -> handleTypingEvent(event)
            is LoginEvent.UiEvent.Click -> handleClickEvent(event)
            is LoginEvent.SystemEvent -> handleSystemEvent(event)
        }
    }

    private fun handleTypingEvent(event: LoginEvent.UiEvent.Typing) {
        when (event) {
            is LoginEvent.UiEvent.Typing.EmailField ->
                stateManager.updateComponentsOnEmailChange(event.text)

            is LoginEvent.UiEvent.Typing.PasswordField ->
                stateManager.updateComponentsOnPasswordChange(event.text)
        }
    }

    private fun handleClickEvent(event: LoginEvent.UiEvent.Click) {
        when (event) {
            LoginEvent.UiEvent.Click.Background ->
                effectManager.setCLearFocusAndHideKeyboardEffect()

            is LoginEvent.UiEvent.Click.CheckBox -> {
                stateManager.updateComponentsOnCheckBoxChange(event.isChecked)
            }

            LoginEvent.UiEvent.Click.EnterButton ->
                onEvent(LoginEvent.SystemEvent.LoginTask.Executing)

            LoginEvent.UiEvent.Click.NewAccountButton ->
                effectManager.setNavigateToNewUserEffect()

            LoginEvent.UiEvent.Click.RecoverPasswordButton ->
                effectManager.setNavigateToForgetPasswordEffect()


        }
    }

    private fun handleSystemEvent(event: LoginEvent.SystemEvent) {
        when (event) {
            LoginEvent.SystemEvent.LoginTask.Executing -> {
                stateManager.setLoadingState()
                tryToLogin()
            }

            LoginEvent.SystemEvent.LoginTask.Success -> launch {
                val isChecked = stateManager.getCheckBoxState()
                preferencesRepository.setKeepLogged(isChecked)
                effectManager.setNavigateToMainEffect()
            }

            is LoginEvent.SystemEvent.LoginTask.CriticalError ->
                effectManager.setNavigateToNotificationEffect()

            is LoginEvent.SystemEvent.LoginTask.RecoverableError -> {
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
                onSuccess = { LoginEvent.SystemEvent.LoginTask.Success },
                onRecoverableError = { LoginEvent.SystemEvent.LoginTask.RecoverableError(it) },
                onCriticalError = { LoginEvent.SystemEvent.LoginTask.CriticalError(it) }
            )
            onEvent(result)
        }
    }

}

