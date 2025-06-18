package com.example.truckercore.view_model._shared.expressions

import com.example.truckercore.view_model._shared._base.reducer.ReducerResult
import com.example.truckercore.view_model._shared._contracts.Effect
import com.example.truckercore.view_model._shared._contracts.State

fun <S : State, E : Effect> ReducerResult<S, E>.handleResult(
    state: (S) -> Unit = {},
    effect: (E) -> Unit = {}
) {
    when (this) {
        ReducerResult.NoChange -> Unit

        is ReducerResult.StateAndEffect -> {
            state(this.state)
            effect(this.effect)
        }

        is ReducerResult.OnlyEffect -> effect(this.effect)

        is ReducerResult.OnlyState -> state(this.state)
    }
}