package com.example.truckercore._shared.classes

data class ButtonState(
    val isEnabled: Boolean = true
) {

    inline fun handle(onEnabled: () -> Unit, onDisabled: () -> Unit) {
        if(isEnabled) onEnabled()
        else onDisabled()
    }

}