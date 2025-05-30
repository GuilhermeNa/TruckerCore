package com.example.truckercore._utils.classes.ui_component

data class TextInputComponent(
    val text: String = "",
    val errorText: String? = null,
    val helperText: String? = null,
    val isValid: Boolean = false,
    override val visibility: Visibility = Visibility.VISIBLE
): UiComponent {

    fun hasError() = errorText != null

    fun hasHelper() = helperText != null

}