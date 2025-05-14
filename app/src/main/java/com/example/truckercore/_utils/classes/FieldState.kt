package com.example.truckercore._utils.classes

data class FieldState(
    val text: String = "",
    val validation: Input = Input.NEUTRAL,
    val stateMessage: String = ""
) {

    inline fun handle(
        onError: (String) -> Unit = {},
        onValid: (String) -> Unit = {},
        onNeutral: () -> Unit = {}
    ) {
        when (validation) {
            Input.NEUTRAL -> onNeutral()
            Input.ERROR -> onError(stateMessage)
            Input.VALID -> onValid(stateMessage)
        }
    }

}

enum class Input {
    NEUTRAL, ERROR, VALID;
}
