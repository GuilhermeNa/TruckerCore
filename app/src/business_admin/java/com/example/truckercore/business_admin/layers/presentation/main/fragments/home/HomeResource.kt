package com.example.truckercore.business_admin.layers.presentation.main.fragments.home

import android.content.res.ColorStateList
import android.graphics.Color

/**
 * HomeResource
 *
 * Responsible for managing ripple color states for [HomeFragment] cards.
 *
 * Separates UI resource logic from Fragment to improve organization
 * and maintain clean responsibility boundaries.
 */
class HomeResource {

    /**
     * Stores the original ripple color when interaction is enabled.
     */
    private var _rippleColor: ColorStateList? = null

    /**
     * Returns enabled ripple color or throws if not initialized.
     */
    private val enabledRes
        get() = _rippleColor ?: throw NullPointerException(ERROR)

    /**
     * Transparent ripple used when interaction is disabled.
     */
    private val disabledRes = ColorStateList.valueOf(Color.TRANSPARENT)

    /**
     * Sets base ripple color only once.
     */
    fun setBaseRipple(color: ColorStateList) {
        if (_rippleColor != null) return
        _rippleColor = color
    }

    /**
     * Returns whether base ripple color has been initialized.
     */
    fun isInitialized() = _rippleColor != null

    /**
     * Returns correct ripple color depending on interaction state.
     */
    fun get(enabled: Boolean) =
        if (enabled) enabledRes else disabledRes

    private companion object {
        private const val ERROR = "Ripple color not initialized"
    }

}