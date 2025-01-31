package com.example.truckercore.shared.utils.expressions

import android.util.Log
import com.example.truckercore.configs.app_constants.Tag

fun logError(message: String) {
    Log.e(Tag.ERROR.getName(), message)
}

fun logWarn(message: String) {
    Log.w(Tag.WARN.getName(), message)
}

fun logError(context: Class<*>, exception: Exception, message: String) {
    Log.e(Tag.ERROR.getName(), "$context | $exception | $message")
}

fun logWarn(context: Class<*>, message: String) {
    Log.w(Tag.WARN.getName(), message)
}

fun logInfo(context: Class<*>, message: String) {
    Log.i(Tag.WARN.getName(), "$context | $message")
}