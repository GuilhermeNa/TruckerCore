package com.example.truckercore.layers.presentation.nav_login.fragments.welcome.navigator

import android.content.Context
import android.content.Intent

class WelcomeNavigatorImpl(private val strategy: WelcomeNavigatorStrategy): WelcomeNavigator {

    override fun nextNavDirection(): Int {
       return strategy.nextNavigationDirection()
    }

    override fun notificationActivityIntent(context: Context): Intent {
        return strategy.notificationActivityIntent(context)
    }

}