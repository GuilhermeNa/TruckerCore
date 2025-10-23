package com.example.truckercore.layers.presentation.viewmodels._shared.components

import com.example.truckercore.domain._shared._contracts.UiComponent

data class CheckBoxComponent(
    val isChecked: Boolean = true,
    override val isEnabled: Boolean = true,
    override val visibility: Visibility = Visibility.VISIBLE
) : UiComponent