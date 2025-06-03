package com.example.truckercore.view.nav_login.fragments.forget_password

import com.example.truckercore.view._shared._base.handlers.StateHandler
import com.example.truckercore.view_model._shared.components.ButtonComponent
import com.example.truckercore.view_model._shared.components.TextInputComponent
import com.example.truckercore.databinding.FragmentForgetPasswordBinding

class ForgetPasswordUiStateHandler : StateHandler<FragmentForgetPasswordBinding>() {

    fun handlePasswordComponent(passComponent: TextInputComponent) {
        bindInputLayout(passComponent, getBinding().fragForgetPassLayout)
    }

    fun handleButtonComponent(buttonComponent: ButtonComponent) {
        bindButton(buttonComponent, getBinding().fragForgetPassButton)
    }

}

