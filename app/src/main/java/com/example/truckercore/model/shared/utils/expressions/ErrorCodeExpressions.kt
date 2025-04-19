package com.example.truckercore.model.shared.utils.expressions

import com.example.truckercore.model.infrastructure.app_exception.ErrorCode

fun ErrorCode.handleOnUi(
    recoverable: () -> Unit,
    fatalError: () -> Unit,
) {
    if(isRecoverable) recoverable()
    else fatalError()
}
