package com.example.truckercore.layers.presentation.nav_login.view.fragments.login.navigator

import com.example.truckercore.domain._shared.components.ButtonComponent

interface LoginFragmentStrategy {

    fun navigateToEmailAuth()

    fun navigateToForgetPassword()

    fun getNewAccountButtonComponent(): ButtonComponent

}