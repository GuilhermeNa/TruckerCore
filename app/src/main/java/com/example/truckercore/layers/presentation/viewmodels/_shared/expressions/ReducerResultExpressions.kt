package com.example.truckercore.layers.presentation.viewmodels._shared.expressions

import com.example.truckercore.domain._shared._contracts.Effect

fun <S : com.example.truckercore.presentation.viewmodels._shared._contracts.State, E : Effect> com.example.truckercore.presentation.viewmodels._shared._base.reducer.ReducerResult<S, E>.handleResult(
    state: (S) -> Unit = {},
    effect: (E) -> Unit = {}
) {
    when (this) {
        com.example.truckercore.presentation.viewmodels._shared._base.reducer.ReducerResult.NoChange -> Unit

        is com.example.truckercore.presentation.viewmodels._shared._base.reducer.ReducerResult.StateAndEffect -> {
            state(this.state)
            effect(this.effect)
        }

        is com.example.truckercore.presentation.viewmodels._shared._base.reducer.ReducerResult.OnlyEffect -> effect(this.effect)

        is com.example.truckercore.presentation.viewmodels._shared._base.reducer.ReducerResult.OnlyState -> state(this.state)
    }
}