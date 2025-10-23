package com.example.truckercore.layers.presentation.nav_login.fragments.forget_password

import androidx.navigation.NavController
import com.example.truckercore.presentation._shared._contracts.Navigator

class ForgetPasswordNavigator(private val navController: NavController): Navigator {

    fun popBackStack() {
        navController.popBackStack()
    }

}