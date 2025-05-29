package com.example.truckercore.view.fragments.login.navigator

import android.content.Context
import android.content.Intent

class LoginNavigatorImpl(private val strategy: LoginNavigatorStrategy) : LoginNavigator {

    override fun navigateToEmailAuth() {
        strategy.navigateToEmailAuth()
    }

    override fun navigateToForgetPassword() {
        strategy.navigateToForgetPassword()
    }

    override fun getMainActivityIntent(context: Context): Intent {
        return strategy.getMainActivityIntent(context)
    }

}