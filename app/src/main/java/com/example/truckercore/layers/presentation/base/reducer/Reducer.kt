package com.example.truckercore.layers.presentation.base.reducer

import com.example.truckercore.layers.presentation.base.contracts.Effect
import com.example.truckercore.layers.presentation.base.contracts.Event
import com.example.truckercore.layers.presentation.base.contracts.State

/**
 * Defines a contract for a reducer in an MVI (Model-View-Intent) architecture.
 *
 * A [Reducer] is responsible for handling events [E] emitted by the View or other
 * sources and producing a new state [S] along with optional side-effects ([F]).
 *
 * The reducer ensures that state transitions are predictable, centralized, and
 * type-safe. It separates **pure state updates** from **one-time effects**, keeping
 * UI logic consistent and testable.
 *
 * @param E The type of [Event] the reducer can handle.
 * @param S The type of [State] managed by the reducer.
 * @param F The type of [Effect] that can be triggered by the reducer.
 */
abstract class Reducer<E : Event, S : State, F : Effect> {

    /**
     * Processes an incoming [event] and calculates the resulting [ReducerResult].
     *
     * This function should be **pure**, meaning it only derives the next state
     * and effects based on the current state and the event, without causing
     * side-effects directly.
     *
     * @param state The current state before the event is applied.
     * @param event The event that triggers a state change or side-effect.
     * @return A [ReducerResult] containing the new state and optional effects.
     */
    abstract fun reduce(state: S, event: E): ReducerResult<S, F>

    protected fun resultWithState(
        state: S
    ): ReducerResult<S, F> = ReducerResult.OnlyState(state)

    protected fun resultWithStateAndEffect(
        state: S, effect: F
    ): ReducerResult<S, F> = ReducerResult.StateAndEffect(state, effect)

    protected fun resultWithEffect(
        effect: F
    ): ReducerResult<S, F> = ReducerResult.OnlyEffect(effect)

    protected fun noChange(): ReducerResult<S, F> = ReducerResult.NoChange

}