package com.example.truckercore.view._shared.expressions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo

@SuppressLint("SourceLockedOrientationActivity")
fun Activity.forcePortraitOrientation() {
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
}

fun Activity.unspecifiedOrientation() {
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
}