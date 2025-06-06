package com.example.truckercore.view._shared._contracts

import android.app.Activity
import com.example.truckercore.view._shared.views.activities.NotificationActivity

interface Navigator {

    fun navigateToNotification(activity: Activity) {
        val intent = NotificationActivity.newInstance(context = activity)
        activity.startActivity(intent)
        activity.finish()
    }

}