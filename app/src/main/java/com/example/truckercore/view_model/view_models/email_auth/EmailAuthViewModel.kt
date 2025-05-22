package com.example.truckercore.view_model.view_models.email_auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore._utils.expressions.launch
import com.example.truckercore._utils.expressions.logEvent
import com.example.truckercore.model.infrastructure.integration.auth.for_app.data.EmailCredential
import com.example.truckercore.model.infrastructure.integration.preferences.PreferencesRepository
import com.example.truckercore.model.modules.authentication.manager.AuthManager
import com.example.truckercore.model.shared.utils.expressions.isEmailFormat
import com.example.truckercore.view.fragments.email_auth.EmailAuthForm
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * EmailAuthViewModel is responsible for managing the state, events, and effects related to the EmailAuthFragment.
 * It handles validation, user creation logic, and emits appropriate UI signals for the fragment to react to.
 *
 * @param authService A service responsible for interacting with the authentication backend.
 */
class EmailAuthViewModel(
    private val preferences: PreferencesRepository,
    private val authService: AuthManager
) : ViewModel() {

    // Gerenciador do Estado da UI
    private val stateManager = EmailAuthUiStateManager()
    val state get() = stateManager.state.asStateFlow()

    // Gerenciador dos Efeitos da UI
    private val effectManager = EmailAuthEffectManager()
    val effect get() = effectManager.effect.asSharedFlow()

    //----------------------------------------------------------------------------------------------
    fun onEvent(event: EmailAuthEvent) = launch {
        logEvent(this@EmailAuthViewModel, event)

        when (event) {
            EmailAuthEvent.UiEvent.Touch.Background -> {
                effectManager.setClearFocusAndHideKeyboardEffect()
            }

            EmailAuthEvent.UiEvent.Click.ButtonAlreadyHaveAccount -> {
                effectManager.setNavigateToLoginEffect()
            }

            is EmailAuthEvent.UiEvent.Click.ButtonCreate -> {
                handleCreateButtonCLicked(event.form)
            }

            EmailAuthEvent.SystemEvent.Success -> TODO()
            EmailAuthEvent.SystemEvent.Failures.ApiResultError -> TODO()
            EmailAuthEvent.SystemEvent.Failures.InputValidationError -> TODO()
        }
    }

    private suspend fun handleCreateButtonCLicked(form: EmailAuthForm) {
        stateManager.setCreatingState()

        delay(500)
        val result = form.validate() // O que retornar?
        if (result.hasFieldError()) {
            onEvent(EmailAuthEvent.SystemEvent.Failures.InputValidationError)
            return
        }

        delay(500)
        tryToAuthenticate(form.getCredential())

    }

    /**
     * Attempts to authenticate the user by first validating input and then creating a new account.
     *
     * If validation fails, an Error state is emitted. Otherwise, authentication proceeds.
     *
     * @param email User input email
     * @param password User input password
     * @param confirmation Password confirmation
     */
    private fun tryToAuthenticate(credential: EmailCredential) {
        viewModelScope.launch {

            val result = authenticateUserWithEmail(credential)
            /*            val newEffect = result.mapAppResult(
                            onSuccess = {
                                markEmailStepComplete()
                                EmailAuthFragEffect.UserCreated
                            },
                            onError = { e -> EmailAuthFragEffect.UserCreationFailed(e) }
                        )
                        setEffect(newEffect)*/
        }
    }

    /**
     * Uses the AuthService to create a user with the provided credentials,
     * and emits a success or failure effect accordingly.
     */
    private suspend fun authenticateUserWithEmail(credential: EmailCredential) =
        authService.createUserWithEmail(credential)

}