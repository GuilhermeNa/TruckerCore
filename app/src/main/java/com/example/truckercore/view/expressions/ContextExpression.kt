package com.example.truckercore.view.expressions

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.truckercore.model.configs.build.Flavor

fun Context.navigateTo(
    clazz: Class<*>,
    intent: Intent.() -> Unit = {}
) {
    Intent(this, clazz).apply {
        intent()
        startActivity(this)
    }
}

fun Context.toast(message: String, length: Int) {
    Toast.makeText(this, message, length).show()
}
