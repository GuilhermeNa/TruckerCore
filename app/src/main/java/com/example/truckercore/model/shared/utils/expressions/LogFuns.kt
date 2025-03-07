package com.example.truckercore.model.shared.utils.expressions

import android.util.Log
import com.example.truckercore.model.configs.app_constants.Tag

fun logError(message: String) {
    Log.e(Tag.ERROR.getName(), message)
}