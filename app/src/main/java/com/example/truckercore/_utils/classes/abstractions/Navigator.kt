package com.example.truckercore._utils.classes.abstractions

import android.app.Activity
import android.content.Context
import com.example.truckercore.view.activities.NotificationActivity
import com.example.truckercore.view.ui_error.UiError

interface Navigator {

    fun navigateToNotification(context: Context, actualActivity: Activity) {
        val intent = NotificationActivity.newInstance(
            context = context,
            title = UiError.Critical().title,
            message = UiError.Critical().message
        )
        actualActivity.navigateUpTo(intent)
    }

}