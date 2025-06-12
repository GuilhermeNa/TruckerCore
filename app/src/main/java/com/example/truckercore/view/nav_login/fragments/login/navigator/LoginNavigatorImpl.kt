package com.example.truckercore.view.nav_login.fragments.login.navigator

class LoginNavigatorImpl(private val strategy: LoginFragmentStrategy) : LoginNavigator {

    override fun navigateToEmailAuth() {
        strategy.navigateToEmailAuth()
    }

    override fun navigateToForgetPassword() {
        strategy.navigateToForgetPassword()
    }

}