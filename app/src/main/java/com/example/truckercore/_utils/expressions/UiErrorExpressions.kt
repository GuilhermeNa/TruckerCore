package com.example.truckercore._utils.expressions

import com.example.truckercore.view.ui_error.UiError

inline fun UiError.handleUiError(
    onRecoverable: (UiError.Recoverable) -> Unit,
    onCritical: (UiError.Critical) -> Unit
) {
    when (this) {
        is UiError.Critical -> onCritical(this)
        is UiError.Recoverable -> onRecoverable(this)
    }
}

inline fun UiError.doIfCritical(
    run: (UiError.Critical) -> Unit
) {
    if(this is UiError.Critical) run(this)
}