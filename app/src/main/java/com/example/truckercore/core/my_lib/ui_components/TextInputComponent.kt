package com.example.truckercore.core.my_lib.ui_components

data class TextInputComponent(
    val text: String = "",
    val errorText: String? = null,
    val helperText: String? = null,
    val isValid: Boolean = false,
    override val visibility: Visibility = Visibility.VISIBLE,
    override val isEnabled: Boolean = true
): UiComponent {

    fun hasError() = errorText != null

    fun hasHelper() = helperText != null

    fun updateText(newTxt: String) = copy(text = newTxt)

}