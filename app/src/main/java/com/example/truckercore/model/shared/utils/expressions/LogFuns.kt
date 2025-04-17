package com.example.truckercore.model.shared.utils.expressions

import android.util.Log
import com.example.truckercore.model.configs.constants.Tag
import com.hbb20.BuildConfig

fun Any.logInfo(message: String) {
    if (BuildConfig.DEBUG) {
        val className = this::class.java.simpleName
        Log.i(Tag.INFO.getName(), "$className: $message")
    }
}

fun Any.logError(message: String) {
    if (BuildConfig.DEBUG) {
        val className = this::class.java.simpleName
        Log.e(Tag.ERROR.getName(), "$className: $message")
    }
}

fun Any.logWar(message: String) {
    if (BuildConfig.DEBUG) {
        val className = this::class.java.simpleName
        Log.e(Tag.WARN.getName(), "$className: $message")
    }
}