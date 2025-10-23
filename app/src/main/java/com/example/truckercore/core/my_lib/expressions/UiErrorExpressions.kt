package com.example.truckercore.core.my_lib.expressions

import com.example.truckercore.domain._shared.helpers.ViewError

inline fun ViewError.handleUiError(
    onRecoverable: (ViewError.Recoverable) -> Unit,
    onCritical: (ViewError.Critical) -> Unit
) {
    when (this) {
        is ViewError.Critical -> onCritical(this)
        is ViewError.Recoverable -> onRecoverable(this)
    }
}

inline fun ViewError.doIfCritical(
    run: (ViewError.Critical) -> Unit
) {
    if (this is ViewError.Critical) run(this)
}