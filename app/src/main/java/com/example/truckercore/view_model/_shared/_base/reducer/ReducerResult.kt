package com.example.truckercore.view_model._shared._base.reducer

import com.example.truckercore.view_model._shared._contracts.Effect
import com.example.truckercore.view_model._shared._contracts.State

sealed class ReducerResult<out S : State, out E : Effect> {

    data class OnlyState<S : State>(val state: S) : ReducerResult<S, Nothing>()
    data class OnlyEffect<E : Effect>(val effect: E) : ReducerResult<Nothing, E>()
    data class StateAndEffect<S : State, E : Effect>(val state: S, val effect: E) :
        ReducerResult<S, E>()
    data object NoChange : ReducerResult<Nothing, Nothing>()

}