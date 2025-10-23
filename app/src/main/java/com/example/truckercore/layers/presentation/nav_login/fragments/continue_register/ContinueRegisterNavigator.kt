package com.example.truckercore.layers.presentation.nav_login.fragments.continue_register

import android.app.Activity
import androidx.navigation.NavController
import com.example.truckercore.presentation._shared._contracts.Navigator

class ContinueRegisterNavigator(
    private val navController: NavController,
    private val flavorService: com.example.truckercore.core.config.flavor.FlavorService
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