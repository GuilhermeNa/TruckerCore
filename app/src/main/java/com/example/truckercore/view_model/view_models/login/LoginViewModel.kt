package com.example.truckercore.view_model.view_models.login

import androidx.lifecycle.viewModelScope
import com.example.truckercore._utils.expressions.logEvent
import com.example.truckercore.view.ui_error.map
import com.example.truckercore.view_model._base.LoggerViewModel
import com.example.truckercore.view_model.view_models.login.effect.LoginEffectManager
import com.example.truckercore.view_model.view_models.login.state.LoginStateManager
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginViewUseCase) : LoggerViewModel() {

    private val stateManager = LoginStateManager()
    val stateFlow get() = stateManager.stateFlow

    private val effectManager = LoginEffectManager()
    val effectFlow get() = effectManager.effectFlow

    //----------------------------------------------------------------------------------------------
    fun onEvent(event: LoginEvent) {
        logEvent(this@LoginViewModel, event)
        when (event) {
            is LoginEvent.UiEvent -> handleUiEvent(event)
            is LoginEvent.SystemEvent -> handleSystemEvent(event)
        }
    }

    private fun handleUiEvent(event: LoginEvent.UiEvent) {
        when (event) {
            is LoginEvent.UiEvent.EmailFieldChanged -> stateManager.updateComponentsOnEmailChange(event.text)
            is LoginEvent.UiEvent.PasswordFieldChanged -> stateManager.updateComponentsOnPasswordChange(event.text)
            LoginEvent.UiEvent.BackGroundCLick -> effectManager.setCLearFocusAndHideKeyboardEffect()
            LoginEvent.UiEvent.ForgetPasswordButtonClick -> effectManager.setNavigateToForgetPasswordEffect()
            LoginEvent.UiEvent.NewAccountButtonClick -> effectManager.setNavigateToNewUserEffect()
            LoginEvent.UiEvent.EnterButtonClick -> {
                onEvent(LoginEvent.SystemEvent.LoginTask.Executing)
                tryToLogin()
            }
        }
    }

    private fun handleSystemEvent(event: LoginEvent.SystemEvent) {
        when (event) {
            LoginEvent.SystemEvent.LoginTask.Executing -> stateManager.setLoadingState()
            LoginEvent.SystemEvent.LoginTask.Success -> effectManager.setNavigateToMainEffect()
            is LoginEvent.SystemEvent.LoginTask.CriticalError -> effectManager.setNavigateToNotificationEffect()
            is LoginEvent.SystemEvent.LoginTask.RecoverableError -> effectManager.setShowToastEffect(event.e.message)
        }
    }

    private fun tryToLogin() {
        viewModelScope.launch {
            val credentials = stateManager.getCredential()
            val result = loginUseCase(credentials).map(
                onSuccess = { LoginEvent.SystemEvent.LoginTask.Success },
                onRecoverableError = { LoginEvent.SystemEvent.LoginTask.RecoverableError(it) },
                onCriticalError = { LoginEvent.SystemEvent.LoginTask.CriticalError(it) }
            )
            onEvent(result)
        }
    }

}

