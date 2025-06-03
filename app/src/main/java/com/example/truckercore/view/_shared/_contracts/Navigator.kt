package com.example.truckercore.view._shared._contracts

import android.app.Activity
import com.example.truckercore.view._shared.views.activities.NotificationActivity
import com.example.truckercore.view_model._shared.helpers.ViewError
import java.lang.ref.WeakReference

interface Navigator {

    fun navigateToNotification(nActivity: WeakReference<Activity>) {
        nActivity.get()?.let { activity ->
            val intent = NotificationActivity.newInstance(
                context = activity,
                title = ViewError.Critical().title,
                message = ViewError.Critical().message
            )
            activity.startActivity(intent)
            activity.finish()

        } ?: throw IllegalArgumentException("Activity was not set or already collected.")
    }

}