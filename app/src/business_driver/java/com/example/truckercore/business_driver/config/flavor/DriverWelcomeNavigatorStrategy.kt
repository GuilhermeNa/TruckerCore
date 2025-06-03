package com.example.truckercore.business_driver.config.flavor

import android.content.Context
import android.content.Intent
import com.example.truckercore.R
import com.example.truckercore.view.activities.NotificationActivity
import com.example.truckercore.view.fragments.welcome.navigator.WelcomeNavigatorStrategy
import com.example.truckercore.view.ui_error.UiError

class DriverWelcomeNavigatorStrategy: WelcomeNavigatorStrategy {

    override fun nextNavigationDirection(): Int {
        return R.id.action_welcomeFragment2_to_loginFragment2
    }

    override fun notificationActivityIntent(context: Context): Intent {
        return NotificationActivity.newInstance(
            context = context,
            title = UiError.Critical().title,
            message = UiError.Critical().title
        )
    }

}