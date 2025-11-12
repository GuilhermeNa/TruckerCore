package com.example.truckercore.layers.presentation.nav_login.fragments.email_auth

import com.example.truckercore.databinding.FragmentEmailAuthBinding
import com.example.truckercore.layers.presentation.base.handlers.StateHandler
import com.example.truckercore.layers.presentation.viewmodels.base.components.ButtonComponent
import com.example.truckercore.core.my_lib.ui_components.TextInputComponent
import com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.state.EmailAuthUiComponents

private typealias Components = EmailAuthUiComponents

class EmailAuthFragmentUiStateHandler : StateHandler<FragmentEmailAuthBinding>() {

    fun handleUiComponents(components: Components) {
        handleEmailComponent(components.emailComponent)
        handlePasswordComponent(components.passwordComponent)
        handleConfirmationComponent(components.confirmationComponent)
        handleCreateAccountButtonComponent(components.createButtonComponent)
    }

    private fun handleEmailComponent(component: TextInputComponent) {
        val view = binding.fragEmailAuthEmailLayout
        bindInputLayout(component, view)
    }

    private fun handlePasswordComponent(component: TextInputComponent) {
        val view = binding.fragEmailAuthPasswordLayout
        bindInputLayout(component, view)
    }

    private fun handleConfirmationComponent(component: TextInputComponent) {
        val view = binding.fragEmailAuthConfirmPasswordLayout
        bindInputLayout(component, view)
    }

    private fun handleCreateAccountButtonComponent(component: ButtonComponent) {
        val view = binding.fragEmailAuthRegisterButton
        bindButton(component, view)
    }

}


