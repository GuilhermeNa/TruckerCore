package com.example.truckercore.core.my_lib.expressions

import android.util.Log
import com.hbb20.BuildConfig

fun Any.logInfo(message: String) {
    if (BuildConfig.DEBUG) {
        val className = this::class.java.simpleName
        Log.i(com.example.truckercore.core.config.enums.Tag.INFO.getName(), "$className: $message")
    }
}

fun Any.logError(message: String) {
    if (BuildConfig.DEBUG) {
        val className = this::class.java.simpleName
        Log.e(com.example.truckercore.core.config.enums.Tag.ERROR.getName(), "$className: $message")
    }
}

fun Any.logWar(message: String) {
    if (BuildConfig.DEBUG) {
        val className = this::class.java.simpleName
        Log.e(com.example.truckercore.core.config.enums.Tag.WARN.getName(), "$className: $message")
    }
}