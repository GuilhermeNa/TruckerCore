package com.example.truckercore.view_model._shared.components

import com.example.truckercore.view_model._shared._contracts.UiComponent

data class CheckBoxComponent(
    val isChecked: Boolean = true,
    override val isEnabled: Boolean = true,
    override val visibility: Visibility = Visibility.VISIBLE
) : UiComponent