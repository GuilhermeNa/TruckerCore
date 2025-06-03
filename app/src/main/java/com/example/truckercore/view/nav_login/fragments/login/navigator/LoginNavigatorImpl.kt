package com.example.truckercore.view.nav_login.fragments.login.navigator

import android.app.Activity
import java.lang.ref.WeakReference

class LoginNavigatorImpl(private val strategy: LoginFragmentStrategy) : LoginNavigator {

    override fun navigateToEmailAuth() {
        strategy.navigateToEmailAuth()
    }

    override fun navigateToForgetPassword() {
        strategy.navigateToForgetPassword()
    }

    override fun navigateToMain(activity: WeakReference<Activity>) {
        strategy.navigateToMain(activity)
    }

}