package com.example.truckercore.layers.presentation.login.view.fragments.login

import com.example.truckercore.databinding.FragmentLoginBinding
import com.example.truckercore.layers.presentation.base.handlers.StateHandler
import com.example.truckercore.layers.presentation.common.LoadingDialog
import com.example.truckercore.layers.presentation.login.view_model.login.LoginViewModel
import com.example.truckercore.layers.presentation.login.view_model.login.helpers.LoginFragmentState
import com.example.truckercore.layers.presentation.login.view_model.login.helpers.LoginFragmentStatus

/**
 * This class is responsible for rendering UI updates for the [LoginFragment]
 * based on the state emitted by the [LoginViewModel].
 *
 * It receives a [FragmentLoginBinding] instance (through [StateHandler]) and
 * updates individual UI components such as error messages, button states,
 * and loading dialog visibility.
 */
class LoginFragmentUiStateHandler : StateHandler<FragmentLoginBinding>() {

    /**
     * Delegates handling of each state component:
     * - status: login flow status (controls dialog and button)
     * - emailMsg: validation message for email field
     * - passMsg: validation message for password field
     */
    fun handle(state: LoginFragmentState, dialog: LoadingDialog) {
        handleStatus(state.status, dialog)
        handleEmailMsg(state.emailMsg)
        handlePassMsg(state.passMsg)
    }

    /**
     * Handles enabling/disabling UI components and showing/dismissing
     * the loading dialog depending on the current login status.
     */
    private fun handleStatus(status: LoginFragmentStatus, dialog: LoadingDialog) {
        when (status) {
            LoginFragmentStatus.WaitingInput -> {
                // User is typing; login button should be disabled
                enableEnterButton(false)
                dialog.dismiss()
            }

            LoginFragmentStatus.ReadyToLogin -> {
                // Input is valid; button enabled, but no loading visible
                enableEnterButton(true)
                dialog.dismiss()
            }

            LoginFragmentStatus.TryingLogin -> {
                // Login initiated; disable button and show loading
                enableEnterButton(false)
                dialog.show()
            }
        }
    }

    /**
     * Sets or clears the password field error message.
     */
    private fun handlePassMsg(passMsg: String?) {
        binding.fragLoginPasswordLayout.error = passMsg
    }

    /**
     * Sets or clears the email field error message.
     */
    private fun handleEmailMsg(emailMsg: String?) {
        binding.fragLoginEmailLayout.error = emailMsg
    }

    /**
     * Enables or disables the login button.
     */
    private fun enableEnterButton(isEnabled: Boolean) {
        binding.fragLoginEnterButton.isEnabled = isEnabled
    }

}