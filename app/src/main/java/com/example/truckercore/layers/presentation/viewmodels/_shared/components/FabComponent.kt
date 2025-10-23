package com.example.truckercore.layers.presentation.viewmodels._shared.components

import com.example.truckercore.domain._shared._contracts.UiComponent

data class FabComponent(
    override val isEnabled: Boolean = true,
    override val visibility: Visibility = Visibility.VISIBLE
): UiComponent