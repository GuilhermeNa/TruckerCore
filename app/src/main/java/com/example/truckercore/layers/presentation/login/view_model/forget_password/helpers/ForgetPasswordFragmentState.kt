package com.example.truckercore.layers.presentation.login.view_model.forget_password.helpers

import com.example.truckercore.layers.presentation.base.contracts.State
import com.example.truckercore.layers.presentation.login.view_model.forget_password.ForgetPasswordViewModel

/**
 * Represents the UI state of the Forget Password Fragment.
 *
 * This sealed class is used by [ForgetPasswordViewModel] to notify
 * the fragment of changes in the input field and the progress of
 * the password reset operation.
 */
sealed class ForgetPasswordFragmentState : State {

    /** Initial state, waiting for the user to enter an email. */
    data object WaitingInput : ForgetPasswordFragmentState()

    /** State when the entered email format is invalid. */
    data object InvalidFormat: ForgetPasswordFragmentState()

    /** State when the entered email is valid and ready to be sent. */
    data object ReadyToSend : ForgetPasswordFragmentState()

    /** State when the reset password request is being processed. */
    data object Sending : ForgetPasswordFragmentState()

    //----------------------------------------------------------------------------------------------
    // Helper properties for easier state checks in the fragment
    //----------------------------------------------------------------------------------------------

    /** Returns true if the state is [ReadyToSend]. */
    val isReadyToSend get() = this is ReadyToSend

    /** Returns true if the state is [Sending]. */
    val isSending get() = this is Sending

    /** Returns true if the state is [InvalidFormat]. */
    val isInvalid get() = this is InvalidFormat

}