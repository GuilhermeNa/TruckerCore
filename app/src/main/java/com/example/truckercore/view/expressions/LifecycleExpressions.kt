package com.example.truckercore.view.expressions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

fun LifecycleCoroutineScope.repeatOnFragmentStart(
    owner: LifecycleOwner, block: suspend () -> Unit
) {
    this.launch {
        owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            block()
        }
    }
}