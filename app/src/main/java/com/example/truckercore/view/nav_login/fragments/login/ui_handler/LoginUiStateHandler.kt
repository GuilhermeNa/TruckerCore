package com.example.truckercore.view.nav_login.fragments.login.ui_handler

import com.example.truckercore.view_model._shared.components.ButtonComponent
import com.example.truckercore.view_model._shared.components.TextInputComponent
import com.example.truckercore.databinding.FragmentLoginBinding

interface LoginUiStateHandler {

    fun initialize(binding: FragmentLoginBinding)

    fun handleEmailComponent(emailInputComponent: TextInputComponent)

    fun handlePasswordComponent(passwordInputComponent: TextInputComponent)

    fun handleEnterBtnComponent(buttonComponent: ButtonComponent)

    fun handleNewAccountBtnComponent(buttonComponent: ButtonComponent)

}