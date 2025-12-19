package com.example.truckercore.layers.presentation.login.view_model.email_auth

import com.example.truckercore.core.my_lib.expressions.handle
import com.example.truckercore.core.my_lib.expressions.isByInvalidCredentials
import com.example.truckercore.core.my_lib.expressions.isByNetwork
import com.example.truckercore.core.my_lib.expressions.isByUserCollision
import com.example.truckercore.core.my_lib.expressions.isByWeakPassword
import com.example.truckercore.core.my_lib.expressions.launchOnViewModelScope
import com.example.truckercore.core.my_lib.files.HALF_SEC
import com.example.truckercore.layers.data.base.outcome.OperationOutcome
import com.example.truckercore.layers.domain.use_case.authentication.CreateUserWithEmailUseCase
import com.example.truckercore.layers.presentation.base.abstractions.view_model.BaseViewModel
import com.example.truckercore.layers.presentation.base.managers.EffectManager
import com.example.truckercore.layers.presentation.base.managers.StateManager
import com.example.truckercore.layers.presentation.login.view_model.email_auth.helpers.EmailAuthenticationFragmentEffect
import com.example.truckercore.layers.presentation.login.view_model.email_auth.helpers.EmailAuthenticationFragmentEvent
import com.example.truckercore.layers.presentation.login.view_model.email_auth.helpers.EmailAuthenticationFragmentReducer
import com.example.truckercore.layers.presentation.login.view_model.email_auth.helpers.EmailAuthenticationFragmentState
import kotlinx.coroutines.delay

private typealias LaunchTaskEffect =
        EmailAuthenticationFragmentEffect.Task.AuthenticateEmail

private typealias TaskCompleteEvent =
        EmailAuthenticationFragmentEvent.AuthenticationTask.Complete

private typealias TaskFailedByNoConnectionEvent =
        EmailAuthenticationFragmentEvent.AuthenticationTask.Failure.NoConnection

private typealias TaskFailedByInvalidCredentialsEvent =
        EmailAuthenticationFragmentEvent.AuthenticationTask.Failure.InvalidCredentials

private typealias TaskFailedByWeakPasswordEvent =
        EmailAuthenticationFragmentEvent.AuthenticationTask.Failure.WeakPassword

private typealias TaskFailedByUserCollisionEvent =
        EmailAuthenticationFragmentEvent.AuthenticationTask.Failure.UserCollision

private typealias TaskFailedByIrrecoverableEvent =
        EmailAuthenticationFragmentEvent.AuthenticationTask.Failure.Irrecoverable

/**
 * ViewModel responsible for managing the "Create Account with Email" workflow.
 *
 * This ViewModel follows an MVI-style architecture:
 * - UI emits [EmailAuthenticationFragmentEvent]
 * - The [EmailAuthenticationFragmentReducer] processes events and produces
 *   either a new [EmailAuthenticationFragmentState] or an [EmailAuthenticationFragmentEffect]
 * - The ViewModel updates state and triggers effects accordingly
 *
 * Responsibilities of this ViewModel include:
 * - Managing real-time form state (email, password, confirmation)
 * - Validating input before triggering authentication
 * - Launching the account creation task through [CreateUserWithEmailUseCase]
 * - Mapping domain-layer results into UI-layer events
 * - Emitting navigation or error effects to the Fragment
 */
class EmailAuthViewModel(
    private val createUserWithEmailUseCase: CreateUserWithEmailUseCase
) : BaseViewModel() {

    /**
     * State manager responsible for holding and updating the current UI state.
     * State updates are exposed to the UI via [stateFlow].
     */
    private val stateManager = StateManager(EmailAuthenticationFragmentState())
    val stateFlow get() = stateManager.stateFlow

    /**
     * Manages one-off transient effects such as:
     * - Navigation signals
     * - Toast/snackbar warnings
     * - Triggering long-running tasks
     */
    private val effectManager = EffectManager<EmailAuthenticationFragmentEffect>()
    val effectFlow get() = effectManager.effectFlow

    /**
     * Reducer responsible for converting user events into new state or effects.
     */
    private val reducer = EmailAuthenticationFragmentReducer()

    //----------------------------------------------------------------------------------------------
    // Event Handling
    //----------------------------------------------------------------------------------------------
    /**
     * Called whenever a new [EmailAuthFragment] occurs in the UI.
     *
     * The event is delegated to the reducer, and the resulting state or effect is propagated
     * via stateManager and effectManager.
     *
     * @param event The UI event being processed.
     */
    fun onEvent(event: EmailAuthenticationFragmentEvent) = launchOnViewModelScope {
        val result = reducer.reduce(stateManager.currentState(), event)
        result.handle(stateManager::update, ::handleEffect)
    }

    /**
     * Handles effects produced by the reducer.
     *
     * Some effects are forwarded directly to the Fragment,
     * while others such as [LaunchTaskEffect] trigger internal ViewModel operations.
     *
     * @param effect The effect generated by the reducer.
     */
    fun handleEffect(effect: EmailAuthenticationFragmentEffect) {
        when (effect) {
            LaunchTaskEffect -> authenticateEmail()
            else -> effectManager.trySend(effect)
        }
    }

    //----------------------------------------------------------------------------------------------
    // Authentication Task
    //----------------------------------------------------------------------------------------------
    /**
     * Performs the email/password account creation request.
     *
     * Steps:
     * 1. Delay for a short period (500ms) to allow the Fragment's loading
     *    animation to play smoothly.
     * 2. Extract the user credentials from the current state.
     * 3. Execute the [CreateUserWithEmailUseCase] to initiate registration.
     * 4. Map the domain-layer outcome into a UI-layer event using [toEvent].
     * 5. Emit the resulting event back through [onEvent] to continue the MVI loop.
     */
    private fun authenticateEmail() = launchOnViewModelScope {
        delay(HALF_SEC) // Waiting for animation on UI

        val credential = stateManager.currentState().getCredential()
        val newEvent = createUserWithEmailUseCase(credential).toEvent()

        // Feed the outcome back into the reducer cycle
        onEvent(newEvent)
    }

    /**
     * Maps an [OperationOutcome] (result of domain-layer authentication attempt)
     * into a corresponding [EmailAuthenticationFragmentEvent].
     *
     * This keeps domain/business logic completely separate from UI logic.
     *
     * @return The event representing the outcome of the operation.
     */
    private fun OperationOutcome.toEvent(): EmailAuthenticationFragmentEvent {
        return when (this) {
            OperationOutcome.Completed -> TaskCompleteEvent

            is OperationOutcome.Failure -> when {
                exception.isByNetwork() -> TaskFailedByNoConnectionEvent
                exception.isByInvalidCredentials() -> TaskFailedByInvalidCredentialsEvent
                exception.isByWeakPassword() -> TaskFailedByWeakPasswordEvent
                exception.isByUserCollision() -> TaskFailedByUserCollisionEvent
                else -> TaskFailedByIrrecoverableEvent
            }
        }
    }

}