package com.example.truckercore.core.my_lib.ui_components

data class CheckBoxComponent(
    val isChecked: Boolean = true,
    override val isEnabled: Boolean = true,
    override val visibility: Visibility = Visibility.VISIBLE
) : UiComponent