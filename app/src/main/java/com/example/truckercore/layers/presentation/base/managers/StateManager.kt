package com.example.truckercore.layers.presentation.base.managers

import com.example.truckercore.layers.presentation.base.contracts.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Manages and exposes a stream of immutable UI states using [StateFlow].
 *
 * The [StateManager] acts as a single source of truth for a given [State],
 * allowing observers to react to state updates in a lifecycle-aware way.
 *
 * @param T The type of state being managed.
 * @param initialState The initial state emitted by the manager.
 */
class StateManager<T : State>(initialState: T) {

    /**
     * Internal mutable state flow.
     * Updates to this flow will be emitted to all collectors.
     */
    private val _stateFlow: MutableStateFlow<T> =
        MutableStateFlow(initialState)

    /**
     * Public, read-only access to the state flow.
     */
    val stateFlow = _stateFlow.asStateFlow()

    /**
     * Returns the current state value synchronously.
     *
     * Useful for one-off reads or decision-making logic.
     */
    fun currentState(): T = _stateFlow.value

    /**
     * Updates the current state.
     *
     * The new state will be emitted to all active collectors.
     *
     * @param state The new state to be set.
     */
    fun update(state: T) {
        _stateFlow.value = state
    }

}