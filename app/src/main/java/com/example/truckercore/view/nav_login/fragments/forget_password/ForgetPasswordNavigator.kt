package com.example.truckercore.view.nav_login.fragments.forget_password

import androidx.navigation.NavController

class ForgetPasswordNavigator(private val navController: NavController): Navigator {

    fun popBackStack() {
        navController.popBackStack()
    }

}