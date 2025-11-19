package com.example.truckercore.layers.presentation.base.reducer

import com.example.truckercore.layers.presentation.base.contracts.Effect
import com.example.truckercore.layers.presentation.base.contracts.State

/**
 * Represents the outcome of a [Reducer.reduce] operation.
 *
 * [ReducerResult] encapsulates the possible results after processing an [Event]
 * in a reducer, including state updates, one-time effects, both, or no changes.
 * This class ensures that state management and effect triggering are explicit
 * and type-safe in an MVI architecture.
 *
 * @param S The type of [State] being managed.
 * @param E The type of [Effect] that may be triggered.
 */
sealed class ReducerResult<out S : State, out E : Effect> {

    /**
     * Represents a result where only the state is updated.
     * Contains the new [state] of type [S].
     */
    data class OnlyState<S : State>(val state: S) : ReducerResult<S, Nothing>()

    /**
     * Represents a result where only a one-time side-effect is triggered.
     * Contains the [effect] of type [E].
     */
    data class OnlyEffect<E : Effect>(val effect: E) : ReducerResult<Nothing, E>()

    /**
     * Represents a result where both state is updated and a side-effect is triggered.
     * Contains the new [state] of type [S] and the [effect] of type [E].
     */
    data class StateAndEffect<S : State, E : Effect>(val state: S, val effect: E) : ReducerResult<S, E>()

    /**
     * Represents a result where neither state nor effect is modified.
     * Useful for events that do not require any update or action.
     */
    data object NoChange : ReducerResult<Nothing, Nothing>()

}