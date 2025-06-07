package com.example.truckercore.view.nav_login.fragments.email_auth

import androidx.navigation.NavController
import com.example.truckercore.view._shared._contracts.Navigator

class EmailAuthNavigator(private val navController: NavController) : Navigator {

    fun navigateToVerifyEmail() {
        val direction = EmailAuthFragmentDirections
            .actionEmailAuthFragmentToVerifyingEmailFragment()
        navController.navigate(direction)
    }

    fun navigateToLogin() {
        val direction = EmailAuthFragmentDirections
            .actionGlobalLoginFragment()
        navController.navigate(direction)
    }

}