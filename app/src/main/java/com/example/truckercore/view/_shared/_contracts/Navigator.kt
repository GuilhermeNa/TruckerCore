package com.example.truckercore.view._shared._contracts

import android.app.Activity
import com.example.truckercore.view._shared.expressions.navigateToNotification
import com.example.truckercore.view._shared.views.activities.NotificationActivity

interface Navigator {

    fun navigateToNotification(current: Activity) {
        current.navigateToNotification()
    }

}