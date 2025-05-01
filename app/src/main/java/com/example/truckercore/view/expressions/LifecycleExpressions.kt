package com.example.truckercore.view.expressions

import androidx.lifecycle.Lifecycle

fun Lifecycle.State.executeOnState(
    onViewResumed: () -> Unit = {},
    onReCreating: () -> Unit = {}
) {
    if (this == Lifecycle.State.RESUMED) onViewResumed()
    else onReCreating()
}