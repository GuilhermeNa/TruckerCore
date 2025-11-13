package com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth

import com.example.truckercore.core.my_lib.expressions.launchOnViewModelScope
import com.example.truckercore.core.my_lib.files.ANIMATION_500MS
import com.example.truckercore.layers.data.base.outcome.OperationOutcome
import com.example.truckercore.layers.domain.use_case.authentication.CreateUserWithEmailUseCase
import com.example.truckercore.layers.presentation.viewmodels.base._base.managers.EffectManager
import com.example.truckercore.layers.presentation.viewmodels.base._base.managers.StateManager
import com.example.truckercore.layers.presentation.viewmodels.base.abstractions.BaseViewModel
import com.example.truckercore.layers.presentation.viewmodels.base.expressions.handle
import com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.effect.EmailAuthenticationFragmentEffect
import com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.event.EmailAuthenticationFragmentEvent
import com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.reducer.EmailAuthenticationFragmentReducer
import com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.state.EmailAuthenticationFragmentState
import kotlinx.coroutines.delay

private typealias TaskComplete =
        EmailAuthenticationFragmentEvent.AuthenticationTask.Complete
private typealias TaskFailedByNoConnection =
        EmailAuthenticationFragmentEvent.AuthenticationTask.Failure.NoConnection
private typealias TaskFailedByInvalidCredentials =
        EmailAuthenticationFragmentEvent.AuthenticationTask.Failure.InvalidCredentials
private typealias TaskFailedByWeakPassword =
        EmailAuthenticationFragmentEvent.AuthenticationTask.Failure.WeakPassword
private typealias TaskFailedByUserCollision =
        EmailAuthenticationFragmentEvent.AuthenticationTask.Failure.UserCollision
private typealias TaskFailedByIrrecoverable =
        EmailAuthenticationFragmentEvent.AuthenticationTask.Failure.Irrecoverable

class EmailAuthViewModel(
    private val createUserWithEmailUseCase: CreateUserWithEmailUseCase
) : BaseViewModel() {

    private val initialUiState by lazy { EmailAuthenticationFragmentState() }

    private val stateManager = StateManager(initialUiState)
    val stateFlow get() = stateManager.stateFlow

    private val effectManager = EffectManager<EmailAuthenticationFragmentEffect>()
    val effectFlow get() = effectManager.effectFlow

    private val reducer = EmailAuthenticationFragmentReducer()

    //----------------------------------------------------------------------------------------------
    fun onEvent(event: EmailAuthenticationFragmentEvent) = launchOnViewModelScope {
        val result = reducer.reduce(stateManager.currentState(), event)
        result.handle(stateManager::update, ::handleEffect)
    }

    fun handleEffect(effect: EmailAuthenticationFragmentEffect) {
        when (effect) {
            EmailAuthenticationFragmentEffect.Task.AuthenticateEmail -> authenticateEmail()
            else -> effectManager::trySend
        }
    }

    private fun authenticateEmail() = launchOnViewModelScope {
        delay(ANIMATION_500MS)
        val credential = stateManager.currentState().getCredential()
        val newEvent = createUserWithEmailUseCase(credential).toEvent()
        onEvent(newEvent)
    }

    private fun OperationOutcome.toEvent(): EmailAuthenticationFragmentEvent {
        return when (this) {
            OperationOutcome.Completed -> TaskComplete
            is OperationOutcome.Failure -> when {
                exception.isByNetwork() -> TaskFailedByNoConnection
                exception.isByInvalidCredentials() -> TaskFailedByInvalidCredentials
                exception.isByWeakPassword() -> TaskFailedByWeakPassword
                exception.isByUserCollision() -> TaskFailedByUserCollision
                else -> TaskFailedByIrrecoverable
            }
        }
    }

}