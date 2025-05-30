package com.example.truckercore.business_driver.view.fragments.splash_driver.navigator

import android.content.Context
import android.content.Intent
import androidx.navigation.NavController
import com.example.truckercore.business_driver.view.fragments.splash_driver.SplashDriverFragmentDirections
import com.example.truckercore.view.activities.NotificationActivity
import com.example.truckercore.view.ui_error.UiError

class SplashDriverNavigatorImpl(private val navController: NavController) : SplashDriverNavigator {

    override fun navigateToWelcome() {
        val direction =
            SplashDriverFragmentDirections.actionSplashDriverFragmentToWelcomeFragment2()
        navController.navigate(direction)
    }

    override fun navigateToLogin() {
        val direction = SplashDriverFragmentDirections.actionSplashDriverFragmentToLoginFragment2()
        navController.navigate(direction)
    }

    override fun notificationActivityIntent(context: Context): Intent {
        return NotificationActivity.newInstance(
            context = context,
            title = UiError.Critical().title,
            message = UiError.Critical().message
        )
    }

}


