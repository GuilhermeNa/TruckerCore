package com.example.truckercore.business_admin.config.flavor

import android.content.Context
import android.content.Intent
import androidx.navigation.NavController
import com.example.truckercore.business_admin.view.activities.MainActivity
import com.example.truckercore.view.fragments.login.LoginFragmentDirections
import com.example.truckercore.view.fragments.login.navigator.LoginNavigatorStrategy

class AdminLoginNavigatorStrategy(private val navController: NavController) :
    LoginNavigatorStrategy {

    override fun navigateToEmailAuth() {
        val direction = LoginFragmentDirections.actionGlobalEmailAuthFragment()
        navController.navigate(direction)
    }

    override fun navigateToForgetPassword() {
        val direction = LoginFragmentDirections.actionLoginFragmentToForgetPasswordFragment()
        navController.navigate(direction)
    }

    override fun getMainActivityIntent(context: Context): Intent =
        Intent(context, MainActivity::class.java)

}