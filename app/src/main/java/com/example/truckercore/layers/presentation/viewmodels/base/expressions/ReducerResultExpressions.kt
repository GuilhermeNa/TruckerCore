package com.example.truckercore.layers.presentation.viewmodels.base.expressions

import com.example.truckercore.layers.presentation.viewmodels.base._base.reducer.ReducerResult
import com.example.truckercore.layers.presentation.viewmodels.base._contracts.Effect
import com.example.truckercore.layers.presentation.viewmodels.base._contracts.State

fun <S : State, E : Effect> ReducerResult<S, E>.handleResult(
    state: (S) -> Unit = {},
    effect: (E) -> Unit = {}
) = when (this) {
    is ReducerResult.StateAndEffect -> sendStateAndEffect(this, state, effect)
    is ReducerResult.OnlyEffect -> effect(this.effect)
    is ReducerResult.OnlyState -> state(this.state)
    ReducerResult.NoChange -> Unit
}

private fun <S : State, E : Effect> sendStateAndEffect(
    result: ReducerResult.StateAndEffect<S, E>,
    state: (S) -> Unit = {},
    effect: (E) -> Unit = {}
) {
    state(result.state)
    effect(result.effect)
}