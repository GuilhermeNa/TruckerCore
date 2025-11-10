package com.example.truckercore.layers.presentation.base.contracts

import android.app.Activity
import com.example.truckercore.layers.presentation.common.NotificationActivity

interface BaseNavigator {

    fun navigateToErrorActivity(current: Activity) {
        val intent = NotificationActivity.newInstance(context = current)
        current.startActivity(intent)
        current.finish()
    }

    fun navigateToNoConnection() {

    }

}