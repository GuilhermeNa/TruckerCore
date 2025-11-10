package com.example.truckercore.layers.presentation.viewmodels.base._base.reducer

import com.example.truckercore.layers.presentation.viewmodels.base._contracts.Effect
import com.example.truckercore.layers.presentation.viewmodels.base._contracts.Event
import com.example.truckercore.layers.presentation.viewmodels.base._contracts.State

abstract class BaseReducer<E : Event, S : State, F : Effect> {

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