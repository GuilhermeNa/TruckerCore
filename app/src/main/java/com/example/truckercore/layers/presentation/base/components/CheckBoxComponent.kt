package com.example.truckercore.layers.presentation.base.components

import com.example.truckercore.core.my_lib.ui_components.UiComponent
import com.example.truckercore.core.my_lib.ui_components.Visibility

data class CheckBoxComponent(
    val isChecked: Boolean = true,
    override val isEnabled: Boolean = true,
    override val visibility: Visibility = Visibility.VISIBLE
) : UiComponent