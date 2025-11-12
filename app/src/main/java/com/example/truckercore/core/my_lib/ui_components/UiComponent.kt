package com.example.truckercore.core.my_lib.ui_components

interface UiComponent {

    val isEnabled: Boolean

    val visibility: Visibility

    fun isVisible() = this.visibility == Visibility.VISIBLE

    fun isGone() = this.visibility == Visibility.GONE

    fun isInvisible() = this.visibility == Visibility.INVISIBLE

}