package com.example.truckercore.view_model.states

sealed class ButtonState {
    data object Enabled: ButtonState()
    data object Disabled: ButtonState()
}