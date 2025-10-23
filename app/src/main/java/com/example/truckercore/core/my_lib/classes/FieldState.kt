package com.example.truckercore.core.my_lib.classes

data class FieldState(
    val text: String = "",
    val status: Input = Input.NEUTRAL,
    val message: String? = null
) {

    enum class Input { NEUTRAL, ERROR, VALID; }

    inline fun handle(
        onError: (String) -> Unit = {},
        onValid: (String) -> Unit = {},
        onNeutral: () -> Unit = {}
    ) {
        when (status) {
            Input.NEUTRAL -> onNeutral()
            Input.ERROR -> message?.let { onError(message) }
            Input.VALID -> message?.let { onValid(message) }
        }
    }


}


