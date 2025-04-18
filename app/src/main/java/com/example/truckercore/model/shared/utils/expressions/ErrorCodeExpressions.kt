package com.example.truckercore.model.shared.utils.expressions

import com.example.truckercore.model.infrastructure.integration.exceptions.ErrorCode

fun ErrorCode.handleOnUi(
    recoverable: () -> Unit,
    fatalError: () -> Unit,
) {
    if(isRecoverable) recoverable()
    else fatalError()
}
