package com.example.truckercore.view.expressions

import android.content.Context
import android.content.Intent

fun Context.navigateTo(
    clazz: Class<*>,
    intent: Intent.() -> Unit = {}
) {
    Intent(this, clazz).apply {
        intent()
        startActivity(this)
    }
}