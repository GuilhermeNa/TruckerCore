package com.example.truckercore.view.nav_login.fragments.welcome.navigator

import android.content.Context
import android.content.Intent

interface WelcomeNavigator {

    fun nextNavDirection(): Int

    fun notificationActivityIntent(context: Context): Intent

}