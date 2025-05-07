package com.example.truckercore._utils.classes

data class FieldState(
    val text: String = "",
    val validation: InputValidation = InputValidation.NEUTRAL,
    val stateMessage: String = ""
) {

    inline fun handle(
        onError: (String) -> Unit = {},
        onValid: (String) -> Unit = {},
        onNeutral: () -> Unit = {}
    ) {
        when (validation) {
            InputValidation.NEUTRAL -> onNeutral()
            InputValidation.ERROR -> onError(stateMessage)
            InputValidation.VALID -> onValid(stateMessage)
        }
    }

}

enum class InputValidation {
    NEUTRAL, ERROR, VALID;
}
