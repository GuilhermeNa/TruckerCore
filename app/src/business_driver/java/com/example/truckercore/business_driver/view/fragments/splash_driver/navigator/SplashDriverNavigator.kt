package com.example.truckercore.business_driver.view.fragments.splash_driver.navigator

import android.content.Context
import android.content.Intent

interface SplashDriverNavigator {

    fun navigateToWelcome()

    fun navigateToLogin()

    fun notificationActivityIntent(context: Context): Intent

}