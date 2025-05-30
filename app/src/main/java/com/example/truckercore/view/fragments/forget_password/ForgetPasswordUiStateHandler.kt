package com.example.truckercore.view.fragments.forget_password

import com.example.truckercore._utils.classes.abstractions.UiStateHandler
import com.example.truckercore._utils.classes.ui_component.ButtonComponent
import com.example.truckercore._utils.classes.ui_component.TextInputComponent
import com.example.truckercore.databinding.FragmentForgetPasswordBinding

class ForgetPasswordUiStateHandler : UiStateHandler<FragmentForgetPasswordBinding>() {

    fun handlePasswordComponent(passComponent: TextInputComponent) {
        bindInputLayout(passComponent, getBinding().fragForgetPassLayout)
    }

    fun handleButtonComponent(buttonComponent: ButtonComponent) {
        bindButton(buttonComponent, getBinding().fragForgetPassButton)
    }

}

