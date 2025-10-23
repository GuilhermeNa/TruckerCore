package com.example.truckercore.layers.presentation.viewmodels._shared._base.reducer

import com.example.truckercore.domain._shared._contracts.Effect

abstract class BaseReducer<E : com.example.truckercore.presentation.viewmodels._shared._contracts.Event, S : com.example.truckercore.presentation.viewmodels._shared._contracts.State, F : Effect> :
    com.example.truckercore.presentation.viewmodels._shared._contracts.Reducer<E, S, F> {

    protected fun resultWithState(state: S): com.example.truckercore.presentation.viewmodels._shared._base.reducer.ReducerResult<S, F> =
        com.example.truckercore.presentation.viewmodels._shared._base.reducer.ReducerResult.OnlyState(
            state
        )

    protected fun resultWithStateAndEffect(state: S, effect: F): com.example.truckercore.presentation.viewmodels._shared._base.reducer.ReducerResult<S, F> =
        com.example.truckercore.presentation.viewmodels._shared._base.reducer.ReducerResult.StateAndEffect(
            state,
            effect
        )

    protected fun resultWithEffect(effect: F): com.example.truckercore.presentation.viewmodels._shared._base.reducer.ReducerResult<S, F> =
        com.example.truckercore.presentation.viewmodels._shared._base.reducer.ReducerResult.OnlyEffect(
            effect
        )

    protected fun noChange(): com.example.truckercore.presentation.viewmodels._shared._base.reducer.ReducerResult<S, F> =
        com.example.truckercore.presentation.viewmodels._shared._base.reducer.ReducerResult.NoChange

}