package com.example.truckercore.business_admin.config.flavor

import android.content.Context
import android.content.Intent
import com.example.truckercore.R
import com.example.truckercore.presentation._shared.views.activities.NotificationActivity
import com.example.truckercore.presentation.nav_login.fragments.welcome.navigator.WelcomeNavigatorStrategy

class AdminWelcomeNavigatorStrategy: WelcomeNavigatorStrategy {

    override fun nextNavigationDirection(): Int {
        return R.id.action_welcomeFragment_to_emailAuthFragment
    }

    override fun notificationActivityIntent(context: Context): Intent {
        return NotificationActivity.newInstance(context = context)
    }

}