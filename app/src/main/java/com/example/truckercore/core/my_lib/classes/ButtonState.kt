package com.example.truckercore.core.my_lib.classes

data class ButtonState(
    val isEnabled: Boolean = true
) {

    inline fun handle(onEnabled: () -> Unit, onDisabled: () -> Unit) {
        if(isEnabled) onEnabled()
        else onDisabled()
    }

}