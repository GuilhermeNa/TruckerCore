package com.example.truckercore.view.fragments.login.navigator

import android.content.Context
import android.content.Intent
import com.example.truckercore.view.activities.NotificationActivity
import com.example.truckercore.view.ui_error.UiError

interface LoginNavigator {

    fun navigateToEmailAuth()

    fun navigateToForgetPassword()

    fun getMainActivityIntent(context: Context): Intent

    fun getNotificationActivityIntent(context: Context): Intent {
        return NotificationActivity.newInstance(
            context = context,
            title = UiError.Critical().title,
            message = UiError.Critical().message
        )
    }

}