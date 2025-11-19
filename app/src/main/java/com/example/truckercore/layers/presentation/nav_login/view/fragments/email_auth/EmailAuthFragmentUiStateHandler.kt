package com.example.truckercore.layers.presentation.nav_login.view.fragments.email_auth

import com.example.truckercore.core.my_lib.ui_components.TextInputComponent
import com.example.truckercore.databinding.FragmentEmailAuthBinding
import com.example.truckercore.layers.presentation.base.handlers.StateHandler
import com.example.truckercore.layers.presentation.common.LoadingDialog
import com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.state.EmailAuthUiStatus
import com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.state.EmailAuthenticationFragmentState

/**
 * UI State Handler for the [EmailAuthFragment].
 *
 * This class acts as the presentation layer responsible for applying
 * visual updates to the Fragment's views based on the latest
 * [EmailAuthenticationFragmentState].
 *
 * Its primary responsibilities include:
 * - Rendering input field errors and visual states
 * - Updating button enabled/disabled states
 * - Displaying or dismissing the loading dialog during authentication
 */
class EmailAuthFragmentUiStateHandler : StateHandler<FragmentEmailAuthBinding>() {

    /**
     * Applies the full UI state to all relevant components.
     *
     * This function acts as the main entry point for rendering, delegating
     * each part of the UI to specialized handlers:
     *
     * - Email input layout
     * - Password input layout
     * - Confirmation input layout
     * - Register button state
     * - Loading dialog visibility
     *
     * @param dialog The [LoadingDialog] used to display loading animations.
     * @param state The current UI state produced by the ViewModel.
     */
    fun handleState(dialog: LoadingDialog, state: EmailAuthenticationFragmentState) {
        handleEmailLayout(state.uiComponents.emailComponent)
        handlePasswordLayout(state.uiComponents.passwordComponent)
        handleConfirmationLayout(state.uiComponents.confirmationComponent)
        handleButton(state.status)
        handleDialog(dialog, state.status)
    }

    //----------------------------------------------------------------------------------------------
    // Text Input Layout Rendering
    //----------------------------------------------------------------------------------------------
    /**
     * Renders the email input layout using the provided [TextInputComponent].
     */
    private fun handleEmailLayout(component: TextInputComponent) {
        val view = binding.fragEmailAuthEmailLayout
        bindInputLayout(component, view)
    }

    /**
     * Renders the password input layout.
     */
    private fun handlePasswordLayout(component: TextInputComponent) {
        val view = binding.fragEmailAuthPasswordLayout
        bindInputLayout(component, view)
    }

    /**
     * Renders the password confirmation input layout.
     */
    private fun handleConfirmationLayout(component: TextInputComponent) {
        val view = binding.fragEmailAuthConfirmPasswordLayout
        bindInputLayout(component, view)
    }

    //----------------------------------------------------------------------------------------------
    // Button Rendering
    //----------------------------------------------------------------------------------------------
    /**
     * Updates the enabled/disabled state of the "Create Account" button.
     *
     * The button is enabled only when:
     * - All fields are valid
     * - No authentication process is currently running
     *
     * The logic for determining readiness is encapsulated in
     * [EmailAuthUiStatus.isReadyToCreate].
     */
    private fun handleButton(status: EmailAuthUiStatus) {
        binding.fragEmailAuthRegisterButton.isEnabled = status.isReadyToCreate()
    }

    //----------------------------------------------------------------------------------------------
    // Loading Dialog Rendering
    //----------------------------------------------------------------------------------------------
    /**
     * Controls the visibility of the loading dialog based on the current
     * authentication status.
     *
     * The dialog is displayed when the user has initiated the authentication
     * process (i.e., when an email/password registration attempt is underway).
     *
     * When the process completes or fails, the dialog is dismissed.
     *
     * @param dialog The loading dialog used to present a progress animation.
     * @param status The status determining whether a task is running.
     */
    private fun handleDialog(dialog: LoadingDialog, status: EmailAuthUiStatus) {
        if (status.isCreating()) {
            dialog.show()
        } else {
            dialog.dismiss()
        }
    }

}