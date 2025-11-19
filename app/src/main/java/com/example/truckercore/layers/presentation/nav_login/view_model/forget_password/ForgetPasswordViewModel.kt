package com.example.truckercore.layers.presentation.nav_login.view_model.forget_password

import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.core.my_lib.expressions.isEmailFormat
import com.example.truckercore.core.my_lib.expressions.launchOnViewModelScope
import com.example.truckercore.layers.data.base.outcome.OperationOutcome
import com.example.truckercore.layers.data.base.outcome.expressions.isConnectionError
import com.example.truckercore.layers.data.base.outcome.expressions.isSuccess
import com.example.truckercore.layers.domain.use_case.authentication.ResetPasswordUseCase
import com.example.truckercore.layers.presentation.base.abstractions.view_model.BaseViewModel
import com.example.truckercore.layers.presentation.base.managers.EffectManager
import com.example.truckercore.layers.presentation.base.managers.StateManager
import com.example.truckercore.layers.presentation.nav_login.view_model.forget_password.effect.ForgetPasswordFragmentEffect
import com.example.truckercore.layers.presentation.nav_login.view_model.forget_password.state.ForgetPasswordFragmentState

/**
 * ViewModel responsible for handling the "Forget Password" feature.
 */
class ForgetPasswordViewModel(
    private val resetPasswordUseCase: ResetPasswordUseCase
) : BaseViewModel() {

    private val stateManager =
        StateManager<ForgetPasswordFragmentState>(ForgetPasswordFragmentState.WaitingInput)
    val stateFlow get() = stateManager.stateFlow

    private val effectManager = EffectManager<ForgetPasswordFragmentEffect>()
    val effectFlow get() = effectManager.effectFlow

    /**
     * Handles sending the reset password email.
     *
     * Updates the UI state to [ForgetPasswordFragmentState.Sending] while processing, then triggers
     * success or failure effects based on the [ResetPasswordUseCase] result.
     *
     * @param text The email entered by the user.
     */
    fun sendEmail(text: String) {
        // Update state to show loading dialog
        stateManager.update(ForgetPasswordFragmentState.Sending)

        launchOnViewModelScope {
            // Parse the email
            val email = Email.from(text)

            // Call the use case to attempt password reset
            val result = resetPasswordUseCase(email)

            // On success, send a navigation effect to go back and notify success
            if (result.isSuccess()) {
                effectManager.trySend(ForgetPasswordFragmentEffect.NavigateBackAndNotifySuccess)
                return@launchOnViewModelScope
            }

            // On failure, reset the state to allow resending
            stateManager.update(ForgetPasswordFragmentState.ReadyToSend)

            // Convert the outcome to an appropriate effect and send it
            val newEffect = result.toEffect()
            effectManager.trySend(newEffect)
        }
    }

    /**
     * Handles email text changes in the input field.
     *
     * Updates the fragment state depending on the validity of the entered email:
     *  - Empty email → [ForgetPasswordFragmentState.WaitingInput]
     *  - Invalid format → [ForgetPasswordFragmentState.InvalidFormat]
     *  - Valid email → [ForgetPasswordFragmentState.ReadyToSend]
     *
     * @param text The email entered by the user.
     */
    fun emailChanged(text: String) {
        val newState = when {
            text.isEmpty() -> ForgetPasswordFragmentState.WaitingInput
            !text.isEmailFormat() -> ForgetPasswordFragmentState.InvalidFormat
            else -> ForgetPasswordFragmentState.ReadyToSend
        }
        stateManager.update(newState)
    }

    /**
     * Maps the [OperationOutcome] of the use case to a one-time effect for the fragment.
     *
     * - Connection errors result in [ForgetPasswordFragmentState.NavigateToNoConnection].
     * - Other failures result in [ForgetPasswordFragmentState.NavigateToNotification].
     */
    private fun OperationOutcome.toEffect() =
        if (this.isConnectionError()) ForgetPasswordFragmentEffect.NavigateToNoConnection
        else ForgetPasswordFragmentEffect.NavigateToNotification

}