package com.example.truckercore.view.expressions

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.truckercore.view.enums.Flavor

fun Context.navigateTo(
    clazz: Class<*>,
    intent: Intent.() -> Unit = {}
) {
    Intent(this, clazz).apply {
        intent()
        startActivity(this)
    }
}

fun Context.getFlavor(): Flavor {
    val appName = applicationContext.packageManager.getApplicationLabel(applicationInfo)
    return Flavor.fromFieldName(appName.toString())
}

fun Context.toast(message: String, length: Int) {
    Toast.makeText(this, message, length).show()
}
