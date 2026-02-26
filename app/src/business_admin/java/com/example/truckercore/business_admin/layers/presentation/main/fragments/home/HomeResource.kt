package com.example.truckercore.business_admin.layers.presentation.main.fragments.home

import android.content.res.ColorStateList
import android.graphics.Color

class HomeResource {

    private var _rippleColor: ColorStateList? = null
    private val enabledRes get() = _rippleColor ?: throw NullPointerException(ERROR)

    private val disabledRes = ColorStateList.valueOf(Color.TRANSPARENT)

    fun setBaseRipple(color: ColorStateList) {
        if (_rippleColor != null) return
        else _rippleColor = color
    }

    fun isInitialized() = _rippleColor != null

    fun get(enabled: Boolean) = if (enabled) enabledRes else disabledRes

    private companion object {
        private const val ERROR = "Ripple color not initialized"
    }

}