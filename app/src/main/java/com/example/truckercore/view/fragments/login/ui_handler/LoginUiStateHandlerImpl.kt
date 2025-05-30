package com.example.truckercore.view.fragments.login.ui_handler

import com.example.truckercore._utils.classes.ui_component.ButtonComponent
import com.example.truckercore._utils.classes.ui_component.TextInputComponent
import com.example.truckercore._utils.classes.ui_component.ViewBinder
import com.example.truckercore.databinding.FragmentLoginBinding
import com.google.android.material.textfield.TextInputLayout
import java.lang.ref.WeakReference

class LoginUiStateHandlerImpl : LoginUiStateHandler {

    private val viewBinder = ViewBinder()

    private var _binding: WeakReference<FragmentLoginBinding>? = null
    private fun getBinding() =
        requireNotNull(_binding?.get()) { "Binding was not set or already collected." }

    override fun initialize(binding: FragmentLoginBinding) {
        _binding = WeakReference(binding)
    }

    override fun handleEmail(emailInputComponent: TextInputComponent) {
        viewBinder.bindTextInput(emailInputComponent, getBinding().fragLoginEmailLayout)
    }

    override fun handlePassword(passwordInputComponent: TextInputComponent) {
        viewBinder.bindTextInput(passwordInputComponent, getBinding().fragLoginEmailLayout)
    }

    override fun handleEnterButton(buttonComponent: ButtonComponent) {
        viewBinder.bindButton(buttonComponent, getBinding().fragLoginEnterButton)
    }

    override fun getFocusableViews(): Array<TextInputLayout> = arrayOf(
        getBinding().fragLoginEmailLayout,
        getBinding().fragLoginPasswordLayout
    )

}