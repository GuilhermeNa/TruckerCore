package com.example.truckercore.view_model._shared._base.reducer

import com.example.truckercore.view_model._shared._contracts.Effect
import com.example.truckercore.view_model._shared._contracts.Event
import com.example.truckercore.view_model._shared._contracts.Reducer
import com.example.truckercore.view_model._shared._contracts.State

abstract class BaseReducer<E : Event, S : State, F : Effect> : Reducer<E, S, F> {

    protected fun resultWithState(state: S): ReducerResult<S, F> =
        ReducerResult.OnlyState(state)

    protected fun resultWithStateAndEffect(state: S, effect: F): ReducerResult<S, F> =
        ReducerResult.StateAndEffect(state, effect)

    protected fun resultWithEffect(effect: F): ReducerResult<S, F> =
        ReducerResult.OnlyEffect(effect)

    protected fun noChange(): ReducerResult<S, F> =
        ReducerResult.NoChange

}