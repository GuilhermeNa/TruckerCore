package com.example.truckercore.view.nav_login.fragments.login.navigator

import com.example.truckercore.view_model._shared.components.ButtonComponent

interface LoginFragmentStrategy {

    fun navigateToEmailAuth()

    fun navigateToForgetPassword()

    fun getNewAccountButtonComponent(): ButtonComponent

}