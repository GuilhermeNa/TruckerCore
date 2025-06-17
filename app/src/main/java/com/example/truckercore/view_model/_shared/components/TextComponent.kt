package com.example.truckercore.view_model._shared.components

import com.example.truckercore.view_model._shared._contracts.UiComponent

data class TextComponent(
    val text: String = "",
    override val isEnabled: Boolean = true,
    override val visibility: Visibility = Visibility.VISIBLE
) : UiComponent {

    fun updateText(newText: String) = copy(text = newText)

}