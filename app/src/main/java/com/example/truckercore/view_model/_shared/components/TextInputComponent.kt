package com.example.truckercore.view_model._shared.components

import com.example.truckercore.view_model._shared._contracts.UiComponent

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

}