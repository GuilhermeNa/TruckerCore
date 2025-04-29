package com.example.truckercore.model.shared.utils.expressions

import com.example.truckercore.model.infrastructure.app_exception.ErrorCode

fun ErrorCode.handleOnUi(
    onRecoverable: () -> Unit,
    onFatalError: () -> Unit,
) {
    if(isRecoverable) onRecoverable()
    else onFatalError()
}
