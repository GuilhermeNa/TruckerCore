package com.example.truckercore.core.my_lib.ui_components

data class TextComponent(
    val text: String = "",
    override val isEnabled: Boolean = true,
    override val visibility: Visibility = Visibility.VISIBLE
) : UiComponent {

    fun updateText(newText: String) = copy(text = newText)

}