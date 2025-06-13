package com.example.truckercore.view._shared.expressions

import android.app.Activity
import com.example.truckercore.view._shared.views.activities.NotificationActivity

fun Activity.navigateToNotification() {
    val intent = NotificationActivity.newInstance(context = this)
    this.startActivity(intent)
    this.finish()
}
