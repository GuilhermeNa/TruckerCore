package com.example.truckercore.view.fragments.login.ui_handler

import com.example.truckercore._utils.classes.ui_component.ButtonComponent
import com.example.truckercore._utils.classes.ui_component.TextInputComponent
import com.example.truckercore.databinding.FragmentLoginBinding
import com.google.android.material.textfield.TextInputLayout

interface LoginUiStateHandler {

    fun initialize(binding: FragmentLoginBinding)

    fun handleEmailComponent(emailInputComponent: TextInputComponent)

    fun handlePasswordComponent(passwordInputComponent: TextInputComponent)

    fun handleEnterBtnComponent(buttonComponent: ButtonComponent)

    fun handleNewAccountBtnComponent(buttonComponent: ButtonComponent)

}