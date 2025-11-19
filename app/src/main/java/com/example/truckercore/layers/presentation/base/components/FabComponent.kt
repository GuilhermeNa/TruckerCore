package com.example.truckercore.layers.presentation.base.components

import com.example.truckercore.core.my_lib.ui_components.Visibility
import com.example.truckercore.domain._shared._contracts.UiComponent

data class FabComponent(
    override val isEnabled: Boolean = true,
    override val visibility: Visibility = Visibility.VISIBLE
): UiComponent