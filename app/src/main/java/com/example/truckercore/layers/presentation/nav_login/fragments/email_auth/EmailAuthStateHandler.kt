package com.example.truckercore.layers.presentation.nav_login.fragments.email_auth

import com.example.truckercore.databinding.FragmentEmailAuthBinding
import com.example.truckercore.presentation._shared._base.handlers.StateHandler
import com.example.truckercore.domain._shared.components.ButtonComponent
import com.example.truckercore.domain._shared.components.TextInputComponent
import com.example.truckercore.domain.view_models.email_auth.uiState.EmailAuthUiComponents

private typealias Components = EmailAuthUiComponents

class EmailAuthStateHandler : StateHandler<FragmentEmailAuthBinding>() {

    fun handleUiComponents(components: Components) {
        handleEmailComponent(components.emailComponent)
        handlePasswordComponent(components.passwordComponent)
        handleConfirmationComponent(components.confirmationComponent)
        handleCreateAccountButtonComponent(components.createButtonComponent)
    }

    private fun handleEmailComponent(component: TextInputComponent) {
        val view = getBinding().fragEmailAuthEmailLayout
        bindInputLayout(component, view)
    }

    private fun handlePasswordComponent(component: TextInputComponent) {
        val view = getBinding().fragEmailAuthPasswordLayout
        bindInputLayout(component, view)
    }

    private fun handleConfirmationComponent(component: TextInputComponent) {
        val view = getBinding().fragEmailAuthConfirmPasswordLayout
        bindInputLayout(component, view)
    }

    private fun handleCreateAccountButtonComponent(component: ButtonComponent) {
        val view = getBinding().fragEmailAuthRegisterButton
        bindButton(component, view)
    }

}


