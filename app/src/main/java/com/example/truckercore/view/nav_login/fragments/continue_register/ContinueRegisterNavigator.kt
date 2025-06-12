package com.example.truckercore.view.nav_login.fragments.continue_register

import android.app.Activity
import androidx.navigation.NavController
import com.example.truckercore.model.configs.flavor.FlavorService
import com.example.truckercore.view._shared._contracts.Navigator

class ContinueRegisterNavigator(
    private val navController: NavController,
    private val flavorService: FlavorService
) : Navigator {

    fun navigateToEmailAuth() {
        val direction = ContinueRegisterFragmentDirections
            .actionContinueRegisterFragmentToEmailAuthFragment()
        navController.navigate(direction)
    }

    fun navigateToVerifyEmail() {
        val direction = ContinueRegisterFragmentDirections
            .actionContinueRegisterFragmentToVerifyingEmailFragment()
        navController.navigate(direction)
    }

    fun navigateToUserName() {
        val direction = ContinueRegisterFragmentDirections
            .actionContinueRegisterFragmentToUserNameFragment()
        navController.navigate(direction)
    }

    fun navigateToLogin() {
        val direction = ContinueRegisterFragmentDirections.actionGlobalLoginFragment()
        navController.navigate(direction)
    }

    fun navigateToMain(current: Activity) = flavorService.navigateToMain(current)

}