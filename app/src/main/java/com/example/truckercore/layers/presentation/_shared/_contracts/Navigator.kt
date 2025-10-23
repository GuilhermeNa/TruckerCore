package com.example.truckercore.layers.presentation._shared._contracts

import android.app.Activity
import com.example.truckercore.presentation._shared.expressions.navigateToNotification

interface Navigator {

    fun navigateToNotification(current: Activity) {
        current.navigateToNotification()
    }

}