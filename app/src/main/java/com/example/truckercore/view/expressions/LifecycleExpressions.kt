package com.example.truckercore.view.expressions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Extension function for [LifecycleOwner] to collect data from a [StateFlow] when the fragment's lifecycle reaches the [Lifecycle.State.STARTED] state.
 * This method ensures that the collection starts when the fragment becomes visible and stops when it is no longer visible or in the background.
 *
 * @param flow The [StateFlow] to collect data from.
 * @param block A suspend lambda function that is executed with each item emitted by the [StateFlow].
 *        The `block` receives the emitted value (`T`) as a parameter, allowing you to handle the state accordingly.
 *        Example:
 *        - If the flow emits a `UserLoggedIn` state, the lambda would execute the handling logic for a logged-in user.
 *        - If the flow emits a `UserNotFound` state, the lambda would handle the "user not found" case.
 */
fun <T> LifecycleOwner.collectOnStarted(
    flow: StateFlow<T>,
    block: suspend (data: T) -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect { block(it) }
        }
    }
}