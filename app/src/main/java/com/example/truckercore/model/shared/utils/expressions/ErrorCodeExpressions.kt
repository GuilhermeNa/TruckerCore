package com.example.truckercore.model.shared.utils.expressions

import com.example.truckercore.model.errors.ErrorCode

fun ErrorCode.handleOnUi(
    onRecoverable: (String) -> Unit,
    onFatalError: (name: String, message: String) -> Unit,
) {
    if(isRecoverable) onRecoverable(userMessage)
    else onFatalError(name, userMessage)
}
