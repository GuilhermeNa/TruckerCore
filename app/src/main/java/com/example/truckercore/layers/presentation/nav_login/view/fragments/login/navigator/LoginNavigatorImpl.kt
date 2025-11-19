package com.example.truckercore.layers.presentation.nav_login.view.fragments.login.navigator

class LoginNavigatorImpl(private val strategy: com.example.truckercore.layers.presentation.nav_login.fragments.login.navigator.LoginFragmentStrategy) :
    com.example.truckercore.layers.presentation.nav_login.fragments.login.navigator.LoginNavigator {

    override fun navigateToEmailAuth() {
        strategy.navigateToEmailAuth()
    }

    override fun navigateToForgetPassword() {
        strategy.navigateToForgetPassword()
    }

}