package com.example.truckercore.business_admin.config.flavor

import androidx.navigation.NavController
import com.example.truckercore.domain._shared.components.ButtonComponent
import com.example.truckercore.domain._shared.components.Visibility
import com.example.truckercore.presentation.nav_login.fragments.login.LoginFragmentDirections
import com.example.truckercore.presentation.nav_login.fragments.login.navigator.LoginFragmentStrategy

class AdminLoginNavigatorStrategy(private val navController: NavController) :
    LoginFragmentStrategy {

    override fun navigateToEmailAuth() {
        val direction = LoginFragmentDirections.actionGlobalEmailAuthFragment()
        navController.navigate(direction)
    }

    override fun navigateToForgetPassword() {
        val direction = LoginFragmentDirections.actionLoginFragmentToForgetPasswordFragment()
        navController.navigate(direction)
    }

    override fun getNewAccountButtonComponent(): ButtonComponent {
        return ButtonComponent(isEnabled = true, visibility = Visibility.VISIBLE)
    }

}