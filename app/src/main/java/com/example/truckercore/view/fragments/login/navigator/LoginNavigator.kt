package com.example.truckercore.view.fragments.login.navigator

import android.content.Context
import android.content.Intent

interface LoginNavigator {

    fun navigateToEmailAuth()

    fun navigateToForgetPassword()

    fun getMainActivityIntent(context: Context): Intent

}