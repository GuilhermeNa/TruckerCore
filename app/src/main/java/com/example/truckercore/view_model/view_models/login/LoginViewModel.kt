package com.example.truckercore.view_model.view_models.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore._utils.classes.Email
import com.example.truckercore._utils.classes.Password
import com.example.truckercore.model.infrastructure.integration.auth.for_app.data.EmailCredential
import com.example.truckercore.model.modules.authentication.manager.AuthManager
import com.example.truckercore._utils.classes.AppResult
import com.example.truckercore._utils.expressions.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Aliases para eventos de UI
private typealias EmailChanged = LoginEvent.UiEvent.EmailFieldChanged
private typealias PasswordChanged = LoginEvent.UiEvent.PasswordFieldChanged
private typealias BackgroundClick = LoginEvent.UiEvent.BackGroundCLick
private typealias ForgetPasswordClick = LoginEvent.UiEvent.ForgetPasswordButtonClick
private typealias NewAccountClick = LoginEvent.UiEvent.NewAccountButtonClick
private typealias EnterClick = LoginEvent.UiEvent.EnterButtonClick

// Aliases para eventos de sistema
private typealias AuthResult = LoginEvent.SystemEvent.AuthenticationResult
private typealias UserRegistered = LoginEvent.SystemEvent.UserRegistered
private typealias AwaitingRegister = LoginEvent.SystemEvent.UserAwaitingRegister

// Aliases para efeitos
private typealias ClearFocusEffect = LoginEffect.ClearFocusAndHideKeyboard
private typealias NavigateToForgetPassword = LoginEffect.Navigation.ForgetPassword
private typealias NavigateToNewAccount = LoginEffect.Navigation.NewAccount
private typealias NavigateToSystem = LoginEffect.Navigation.EnterSystem
private typealias NavigateToCompleteRegister = LoginEffect.Navigation.CompleteRegister


class LoginViewModel(private val authService: AuthManager) : ViewModel() {

    private val stateHandler = LoginStateFlowHandler()
    val state get() = stateHandler.state.asStateFlow()

    private val _effect = MutableSharedFlow<LoginEffect>()
    val effect get() = _effect.asSharedFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            // Ui Events
            is EmailChanged -> stateHandler.updateEmail(event.text)
            is PasswordChanged-> stateHandler.updatePassword(event.text)
            is BackgroundClick -> setEffect(ClearFocusEffect)
            is ForgetPasswordClick -> setEffect(NavigateToForgetPassword)
            is NewAccountClick -> setEffect(NavigateToNewAccount)
            is EnterClick -> {
                setEffect(NavigateToSystem)
                //stateHandler.loading()
                //tryToLogin()
            }

            // System Events
            is AuthResult -> handleResult(event.result)
            is UserRegistered -> setEffect(NavigateToSystem)
            is AwaitingRegister -> setEffect(NavigateToCompleteRegister)
          //  is AuthError -> setEffect(ShowErrorEffect(event.error.errorCode))

        }
    }

    private fun tryToLogin() {
        viewModelScope.launch {
            val result = authService.signIn(getCredential())
            onEvent(AuthResult(result))
        }
    }

    private fun handleResult(loginResult: AppResult<Unit>) {

    }

    private fun getCredential(): EmailCredential {
        val email = Email.from(stateHandler.state.value.email.text)
        val password = Password.from(stateHandler.state.value.password.text)
        return EmailCredential(email, password)
    }

    private fun setEffect(newEffect: LoginEffect) {
        viewModelScope.launch {
            _effect.emit(newEffect)
        }
    }

}

private class LoginStateFlowHandler {

    private val _state: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    private val passwordState get() = state.value.password
    private val emailState get() = state.value.email
    val state get() = _state

    fun updatePassword(password: String) {
        val newPasswordState = LoginValidator.getPasswordState(password)

        val newButtonState = LoginValidator.getButtonState(
            emailValidation = emailState.validation,
            passwordValidation = newPasswordState.validation,
        )

        val newState = _state.value.copy(
            password = newPasswordState,
            buttonEnabled = newButtonState
        )

        setState(newState)
    }

    fun updateEmail(email: String) {
        val newEmailState = LoginValidator.getEmailState(email)

        val newButtonState = LoginValidator.getButtonState(
            emailValidation = newEmailState.validation,
            passwordValidation = passwordState.validation,
        )

        val newState = _state.value.copy(
            email = newEmailState,
            buttonEnabled = newButtonState
        )

        setState(newState)
    }

    fun loading() {
        val newState = _state.value.copy(loading = true)
        setState(newState)
    }

    private fun setState(newState: LoginUiState) {
        _state.value = newState
    }

}