package com.example.truckercore.core.my_lib.ui_components

import com.example.truckercore.layers.presentation.viewmodels.base.components.Visibility

data class Fab(
    val visibility: Visibility = Visibility.VISIBLE,
    val isEnabled: Boolean = true
) {

    fun isVisible() = this.visibility == Visibility.VISIBLE

    fun isGone() = this.visibility == Visibility.GONE

}