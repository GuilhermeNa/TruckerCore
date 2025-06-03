package com.example.truckercore.view_model._shared.components

import com.example.truckercore.view_model._shared._contracts.UiComponent

data class ButtonComponent(
    override val isEnabled: Boolean = true,
    override val visibility: Visibility = Visibility.VISIBLE
): UiComponent {



}
