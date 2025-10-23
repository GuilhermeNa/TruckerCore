package com.example.truckercore.layers.presentation.viewmodels._shared._base.reducer

import com.example.truckercore.domain._shared._contracts.Effect

sealed class ReducerResult<out S : com.example.truckercore.presentation.viewmodels._shared._contracts.State, out E : Effect> {

    data class OnlyState<S : com.example.truckercore.presentation.viewmodels._shared._contracts.State>(val state: S) : com.example.truckercore.presentation.viewmodels._shared._base.reducer.ReducerResult<S, Nothing>()
    data class OnlyEffect<E : Effect>(val effect: E) : com.example.truckercore.presentation.viewmodels._shared._base.reducer.ReducerResult<Nothing, E>()
    data class StateAndEffect<S : com.example.truckercore.presentation.viewmodels._shared._contracts.State, E : Effect>(val state: S, val effect: E) :
        com.example.truckercore.presentation.viewmodels._shared._base.reducer.ReducerResult<S, E>()
    data object NoChange : com.example.truckercore.presentation.viewmodels._shared._base.reducer.ReducerResult<Nothing, Nothing>()

}