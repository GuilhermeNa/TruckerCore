package com.example.truckercore.layers.presentation.nav_login.fragments.login.ui_handler

import com.example.truckercore.domain._shared.components.ButtonComponent
import com.example.truckercore.domain._shared.components.TextInputComponent
import com.example.truckercore.presentation._shared.helpers.ViewBinder
import com.example.truckercore.databinding.FragmentLoginBinding
import java.lang.ref.WeakReference

class LoginUiStateHandlerImpl :
    com.example.truckercore.presentation.nav_login.fragments.login.ui_handler.LoginUiStateHandler {

    private val viewBinder = ViewBinder

    private var _binding: WeakReference<FragmentLoginBinding>? = null
    private fun getBinding() = requireNotNull(_binding?.get()) {
        "Binding was not set or already collected."
    }

    override fun initialize(binding: FragmentLoginBinding) {
        _binding = WeakReference(binding)
    }

    override fun handleEmailComponent(emailInputComponent: TextInputComponent) {
        viewBinder.bindTextInput(emailInputComponent, getBinding().fragLoginEmailLayout)
    }

    override fun handlePasswordComponent(passwordInputComponent: TextInputComponent) {
        viewBinder.bindTextInput(passwordInputComponent, getBinding().fragLoginPasswordLayout)
    }

    override fun handleEnterBtnComponent(buttonComponent: ButtonComponent) {
        viewBinder.bindButton(buttonComponent, getBinding().fragLoginEnterButton)
    }

    override fun handleNewAccountBtnComponent(buttonComponent: ButtonComponent) {
        viewBinder.bindButton(buttonComponent, getBinding().fragLoginNewAccountButton)
    }

}