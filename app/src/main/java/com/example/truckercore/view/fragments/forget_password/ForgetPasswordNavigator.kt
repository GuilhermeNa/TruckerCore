package com.example.truckercore.view.fragments.forget_password

import androidx.navigation.NavController
import com.example.truckercore._utils.classes.abstractions.Navigator

class ForgetPasswordNavigator(private val navController: NavController): Navigator {

    fun popBackStack() {
        navController.popBackStack()
    }

}